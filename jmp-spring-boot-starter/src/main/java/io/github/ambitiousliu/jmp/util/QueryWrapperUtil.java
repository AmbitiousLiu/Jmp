package io.github.ambitiousliu.jmp.util;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.github.ambitiousliu.jmp.context.IdContext;
import io.github.ambitiousliu.jmp.exception.JmpException;
import org.apache.ibatis.reflection.Reflector;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * @author ambitious liu
 * @since 2022-06-18
 */
public class QueryWrapperUtil {
    private QueryWrapperUtil() {

    }

    public static <T> LambdaQueryWrapper<T> mkQueryWrapper(T entity) {
        LambdaQueryWrapper<T> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.setEntity(entity);
        return lambdaQueryWrapper;
    }

    public static <T> QueryWrapper<T> mkIdQueryWrapper(T entity) {
        Map<Field, String> parse = IdContext.parse(entity);
        QueryWrapper<T> queryWrapper = new QueryWrapper<>();
        for (Map.Entry<Field, String> entry : parse.entrySet()) {
            Reflector reflector = new Reflector(entity.getClass());
            Object invoke;
            try {
                invoke = reflector.getGetInvoker(entry.getKey().getName()).invoke(entity, null);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new JmpException("执行getter方法失败", e);
            }
            queryWrapper.eq(entry.getValue(), invoke);
        }
        return queryWrapper;
    }

}
