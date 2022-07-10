package io.github.ambitiousliu.jmp.conditions;

import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.baomidou.mybatisplus.core.toolkit.LambdaUtils;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.support.ColumnCache;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.core.toolkit.support.SerializedLambda;
import org.apache.ibatis.reflection.property.PropertyNamer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static java.util.stream.Collectors.joining;

/**
 * @author ambitious liu
 * @since 2022/7/9
 */
public abstract class AbstractLambdaJoinWrapper<T, Children extends AbstractLambdaJoinWrapper<T, Children>>
        extends AbstractJoinWrapper<T, SFunction<T, ?>, Children> {

    protected Map<String, ColumnCache> columnMap = null;
    protected boolean initColumnMap = false;

    protected AbstractLambdaJoinWrapper(String columnPrefix, AbstractJoinWrapper<?, ?, ?> targetWrapper) {
        super(columnPrefix, targetWrapper);
    }

    @SafeVarargs
    @Override
    protected final String columnsToString(SFunction<T, ?>... columns) {
        return columnsToString(true, columns);
    }

    @SuppressWarnings("unchecked")
    protected String columnsToString(boolean onlyColumn, SFunction<T, ?>... columns) {
        return Arrays.stream(columns).map(i -> columnToString(i, onlyColumn)).collect(joining(StringPool.COMMA));
    }

    @Override
    protected String columnToString(SFunction<T, ?> column) {
        return columnToString(column, true);
    }

    protected String columnToString(SFunction<T, ?> column, boolean onlyColumn) {
        return getColumn(LambdaUtils.resolve(column), onlyColumn);
    }

    protected String getColumn(SerializedLambda lambda, boolean onlyColumn) {
        Class<?> aClass = lambda.getInstantiatedType();
        tryInitCache(aClass);
        String fieldName = PropertyNamer.methodToProperty(lambda.getImplMethodName());
        ColumnCache columnCache = getColumnCache(fieldName, aClass);
        return onlyColumn ? columnCache.getColumn() : columnCache.getColumnSelect();
    }

    protected void tryInitCache(Class<?> lambdaClass) {
        if (!initColumnMap) {
            final Class<T> entityClass = getEntityClass();
            if (entityClass != null) {
                lambdaClass = entityClass;
            }
            Map<String, ColumnCache> map = LambdaUtils.getColumnMap(lambdaClass);
            if (map != null) {
                columnMap = new HashMap<>(map.size());
                map.forEach((key, value) -> {
                    ColumnCache columnCache = new ColumnCache(columnPrefix + value.getColumn(), columnPrefix + value.getColumnSelect());
                    columnMap.put(key, columnCache);
                });
            }
            initColumnMap = true;
        }
        Assert.notNull(columnMap, "can not find lambda cache for this entity [%s]", lambdaClass.getName());
    }

    protected ColumnCache getColumnCache(String fieldName, Class<?> lambdaClass) {
        ColumnCache columnCache = columnMap.get(LambdaUtils.formatKey(fieldName));
        Assert.notNull(columnCache, "can not find lambda cache for this property [%s] of entity [%s]",
                fieldName, lambdaClass.getName());
        return columnCache;
    }
}
