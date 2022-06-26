package io.github.ambitiousliu.jmp.context;

import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.core.toolkit.LambdaUtils;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.core.toolkit.support.SerializedLambda;
import io.github.ambitiousliu.jmp.exception.ParseException;
import org.apache.ibatis.reflection.property.PropertyNamer;

import java.util.List;

/**
 * @author ambitious liu
 * @since 2022-06-19
 */
public class ColumnContext {

    public static <T> String parse(SFunction<T, ?> column) {
        SerializedLambda lambda = LambdaUtils.resolve(column);
        Class<?> aClass = lambda.getInstantiatedType();
        TableInfo tableInfo = TableInfoHelper.getTableInfo(aClass);
        if (tableInfo == null) {
            throw new ParseException("can not find table info, make sure the entity has target mapper");
        }
        List<TableFieldInfo> fieldList = tableInfo.getFieldList();
        String fieldName = PropertyNamer.methodToProperty(lambda.getImplMethodName());
        for (TableFieldInfo tableFieldInfo : fieldList) {
            if (tableFieldInfo.getField().getName().equals(fieldName)) {
                return tableFieldInfo.getColumn();
            }
        }
        throw new ParseException("can not find column: " + column);
    }
}
