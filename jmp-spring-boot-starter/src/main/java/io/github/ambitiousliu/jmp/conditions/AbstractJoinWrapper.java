package io.github.ambitiousliu.jmp.conditions;

import com.baomidou.mybatisplus.core.conditions.AbstractWrapper;
import com.baomidou.mybatisplus.core.conditions.ISqlSegment;
import com.baomidou.mybatisplus.core.enums.SqlKeyword;
import com.baomidou.mybatisplus.core.enums.SqlLike;
import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.sql.SqlUtils;
import io.github.ambitiousliu.jmp.exception.ParseException;
import org.apache.ibatis.reflection.Reflector;
import org.apache.ibatis.reflection.invoker.Invoker;
import org.springframework.util.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;

import static com.baomidou.mybatisplus.core.enums.SqlKeyword.*;
import static com.baomidou.mybatisplus.core.enums.WrapperKeyword.APPLY;
import static java.util.stream.Collectors.joining;

/**
 * @author ambitious liu
 * @since 2022/7/9
 */
public abstract class AbstractJoinWrapper<T, R, Children extends AbstractJoinWrapper<T, R, Children>>
        extends AbstractWrapper<T, R, Children> {

    protected final String columnPrefix;
    protected T joinEntity;
    protected List<ISqlSegment[]> sqlSegmentList = new ArrayList<>();

    private boolean prepared = false;
    private boolean preparedEntity = false;

    private final AbstractJoinWrapper<?, ?, ?> targetWrapper;

    protected AbstractJoinWrapper(String columnPrefix, AbstractJoinWrapper<?, ?, ?> targetWrapper) {
        this.columnPrefix = columnPrefix;
        this.targetWrapper = targetWrapper;
    }

    public T getJoinEntity() {
        return joinEntity;
    }

    public Children setJoinEntity(T entity) {
        joinEntity = entity;
        return typedThis;
    }

    /**
     * 请使用 {@link #getJoinEntity()}
     * @return
     */
    @Deprecated
    @Override
    public T getEntity() {
        return null;
    }

    /**
     * 请使用 {@link #setJoinEntity(Object)}
     * @param entity
     * @return
     */
    @Deprecated
    @Override
    public Children setEntity(T entity) {
        return setJoinEntity(entity);
    }

    @Override
    protected Children addCondition(boolean condition, R column, SqlKeyword sqlKeyword, Object val) {
        return doIt(condition, () -> columnToString(column), sqlKeyword, () -> targetWrapper.formatSql("{0}", val));
    }

    @Override
    protected Children likeValue(boolean condition, SqlKeyword keyword, R column, Object val, SqlLike sqlLike) {
        return doIt(condition, () -> columnToString(column), keyword, () -> targetWrapper.formatSql("{0}", SqlUtils.concatLike(val, sqlLike)));
    }

    @Override
    public Children between(boolean condition, R column, Object val1, Object val2) {
        return doIt(condition, () -> columnToString(column), BETWEEN, () -> formatSql("{0}", val1), AND,
                () -> targetWrapper.formatSql("{0}", val2));
    }

    @Override
    public Children notBetween(boolean condition, R column, Object val1, Object val2) {
        return doIt(condition, () -> columnToString(column), NOT_BETWEEN, () -> formatSql("{0}", val1), AND,
                () -> targetWrapper.formatSql("{0}", val2));
    }

    @Override
    public Children apply(boolean condition, String applySql, Object... value) {
        return doIt(condition, APPLY, () -> targetWrapper.formatSql(applySql, value));
    }

    @Override
    public Children having(boolean condition, String sqlHaving, Object... params) {
        return doIt(condition, HAVING, () -> targetWrapper.formatSqlIfNeed(condition, sqlHaving, params));
    }

    @Override
    public Children in(boolean condition, R column, Collection<?> coll) {
        return doIt(condition, () -> columnToString(column), IN, inExpression(coll));
    }

    @Override
    public Children notIn(boolean condition, R column, Collection<?> coll) {
        return doIt(condition, () -> columnToString(column), NOT_IN, inExpression(coll));
    }

    protected ISqlSegment inExpression(Collection<?> value) {
        return () -> value.stream().map(i -> targetWrapper.formatSql("{0}", i))
                .collect(joining(StringPool.COMMA, StringPool.LEFT_BRACKET, StringPool.RIGHT_BRACKET));
    }

    protected void parseEntity(String prefix, T entity, BiConsumer<String, Object> consumer) {
        if (entity == null) {
            return;
        }

        // 获取表信息
        TableInfo tableInfo = TableInfoHelper.getTableInfo(entity.getClass());
        Reflector reflector = new Reflector(entity.getClass());

        // 前缀为空则用表名
        if (!StringUtils.hasText(prefix)) {
            prefix = tableInfo.getTableName();
        }

        // parse key
        if (tableInfo.getKeyProperty() != null) {
            Invoker getInvoker = reflector.getGetInvoker(tableInfo.getKeyProperty());
            Object invoke;
            try {
                invoke = getInvoker.invoke(entity, null);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new ParseException(e);
            }
            if (!Objects.isNull(invoke)) {
                consumer.accept(prefix + tableInfo.getKeyColumn(), invoke);
            }
        }

        // parse filed
        // 获取 key -> value，并传递给消费者
        for (TableFieldInfo tableFieldInfo : tableInfo.getFieldList()) {
            Invoker getFiledInvoker = reflector.getGetInvoker(tableFieldInfo.getField().getName());
            Object filedInvoke;
            try {
                filedInvoke = getFiledInvoker.invoke(entity, null);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new ParseException(e);
            }
            if (!Objects.isNull(filedInvoke)) {
                consumer.accept(prefix + tableFieldInfo.getColumn(), filedInvoke);
            }
        }
    }

    @Override
    protected String columnToString(R column) {
        return columnPrefix + column;
    }

    @Override
    protected Children doIt(boolean condition, ISqlSegment... sqlSegments) {
        if (condition) {
            sqlSegmentList.add(sqlSegments);
        }
        return typedThis;
    }

    @Override
    public void clear() {
        super.clear();
        sqlSegmentList.clear();
    }

    public final void merge(AbstractJoinWrapper<?, ?, ?>... abstractJoinWrappers) {
        for (AbstractJoinWrapper<?, ?, ?> wrapper : abstractJoinWrappers) {
            wrapper.prepareEntity(this);
            sqlSegmentList.addAll(wrapper.sqlSegmentList);
        }
    }

    protected void prepareEntity(AbstractJoinWrapper<?, ?, ?> joinWrapper) {
        if (!preparedEntity) {
            preparedEntity = true;
            parseEntity(columnPrefix, getJoinEntity(), (key, value) -> {
                doIt(true, () -> key, EQ, () -> joinWrapper.formatSql("{0}", value));
            });
        }
    }

    public void prepare() {
        if (!prepared) {
            prepared = true;
            prepareEntity(this);
            sqlSegmentList.forEach(iSqlSegment -> expression.add(iSqlSegment));
        }
    }

    public final void mergeAndPrepare(AbstractJoinWrapper<?, ?, ?>... abstractJoinWrappers) {
        merge(abstractJoinWrappers);
        prepare();
    }
}
