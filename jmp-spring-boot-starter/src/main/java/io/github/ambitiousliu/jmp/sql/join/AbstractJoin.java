package io.github.ambitiousliu.jmp.sql.join;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import io.github.ambitiousliu.jmp.constant.OrderMode;
import io.github.ambitiousliu.jmp.exception.ParseException;
import io.github.ambitiousliu.jmp.sql.SqlMaker;
import org.apache.ibatis.reflection.Reflector;
import org.apache.ibatis.reflection.invoker.Invoker;
import org.springframework.util.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

/**
 * @author ambitious liu
 * @since 2022-06-19
 */
public abstract class AbstractJoin implements SqlMaker {
    public <T, E> QueryWrapper<T> mkQueryWrapper(String prefix, E entity, QueryWrapper<T> queryWrapper) {
        TableInfo tableInfo = TableInfoHelper.getTableInfo(entity.getClass());
        Reflector reflector = new Reflector(entity.getClass());
        if (!StringUtils.hasText(prefix)) {
            prefix = tableInfo.getTableName();
        }
        if (queryWrapper == null) {
            queryWrapper = new QueryWrapper<>();
        }
        for (TableFieldInfo tableFieldInfo : tableInfo.getFieldList()) {
            Invoker getInvoker = reflector.getGetInvoker(tableFieldInfo.getField().getName());
            Object invoke;
            try {
                invoke = getInvoker.invoke(entity, null);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new ParseException(e);
            }
            if (!Objects.isNull(invoke)) {
                queryWrapper.eq(prefix + "." + tableFieldInfo.getColumn(), invoke);
            }
        }
        return queryWrapper;
    }

    abstract <R> QueryWrapper<R> mkQueryWrapper();

    abstract <R> QueryWrapper<R> setOrder(QueryWrapper<R> queryWrapper, OrderMode orderMode);
}
