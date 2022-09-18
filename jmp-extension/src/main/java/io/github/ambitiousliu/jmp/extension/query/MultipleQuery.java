package io.github.ambitiousliu.jmp.extension.query;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import io.github.ambitiousliu.jmp.extension.annotation.JmpMultiple;
import io.github.ambitiousliu.jmp.util.WrapperInUtil;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author ambitious liu
 * @since 2022/9/18
 */
public abstract class MultipleQuery<T> implements Query<T> {

    Map<String, List<Object>> multiple;

    public Map<String, List<Object>> getMultiple() {
        return multiple;
    }

    public MultipleQuery<T> setMultiple(Map<String, List<Object>> multiple) {
        this.multiple = multiple;
        return this;
    }

    protected QueryWrapper<T> mkMultipleQuery() {
        // 获取实体类表信息
        ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
        Class clazz = (Class) type.getActualTypeArguments()[0];
        TableInfo tableInfo = TableInfoHelper.getTableInfo(clazz);
        QueryWrapper<T> queryWrapper = new QueryWrapper<>();

        // 获取query类极其父类属性
        Field[] fields = FieldUtils.getAllFields(this.getClass());
        Map<String, Field> fieldsMap = new HashMap<>();
        for (Field field : fields) {
            fieldsMap.putIfAbsent(field.getName(), field);
        }
        // 根据属性名去重
        List<Field> multipleFields = fieldsMap.values().stream().filter(field -> {
            JmpMultiple annotation = field.getAnnotation(JmpMultiple.class);
            return annotation != null;
        }).collect(Collectors.toList());
        // 获取参数的值封装query
        for (Field field : multipleFields) {
            List<Object> objects = multiple.get(field.getName());
            if (field.getName().equals(tableInfo.getKeyProperty())) {
                WrapperInUtil.in(queryWrapper, tableInfo.getKeyColumn(), objects, WrapperInUtil::DO_NOTHING, WrapperInUtil::IS_NULL);
            } else {
                List<TableFieldInfo> fieldList = tableInfo.getFieldList();
                fieldList.forEach(fieldInfo -> {
                    if (fieldInfo.getField().getName().equals(field.getName())) {
                        WrapperInUtil.in(queryWrapper, fieldInfo.getColumn(), objects, WrapperInUtil::DO_NOTHING, WrapperInUtil::IS_NULL);
                    }
                });
            }
        }
        return queryWrapper;
    }
}
