package io.github.ambitiousliu.jmp.context;

import com.google.common.base.CaseFormat;
import io.github.ambitiousliu.jmp.annotation.JmpId;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author ambitious liu
 * @since 2022-06-18
 */
public class IdContext {

    private static final ConcurrentHashMap<Class<?>, Map<Field, String>> map = new ConcurrentHashMap<>(16);

    private IdContext() {
    }

    public static <T> Map<Field, String> parse(T entity) {
        Map<Field, String> fields = map.get(entity.getClass());
        if (fields == null) {
            fields= parseId(entity);
            map.put(entity.getClass(), fields);
        }
        return fields;
    }

    private static <T> Map<Field, String> parseId(T entity) {
        Class<?> clazz = entity.getClass();
        Field[] fields = clazz.getDeclaredFields();
        Map<Field, String> idInfo = new HashMap<>(4);
        for (Field field : fields) {
            JmpId annotation = field.getAnnotation(JmpId.class);
            if (annotation != null) {
                String value = annotation.value();
                if (!StringUtils.hasText(value)) {
                    value = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, field.getName());
                }
                idInfo.put(field, value);
            }
        }
        return idInfo;
    }
}
