package io.github.ambitiousliu.jmp.sql.join;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import io.github.ambitiousliu.jmp.constant.JoinMode;
import io.github.ambitiousliu.jmp.constant.OrderMode;
import io.github.ambitiousliu.jmp.context.ColumnContext;

/**
 * @author ambitious liu
 * @since 2022-06-19
 */
public class Join<T, E> extends AbstractJoin {

    T entity1;
    JoinMode joinMode;
    E entity2;
    String column1;
    String column2;

    public Join(T entity1, JoinMode joinMode, E entity2, String column1, String column2) {
        this.entity1 = entity1;
        this.joinMode = joinMode;
        this.entity2 = entity2;
        this.column1 = column1;
        this.column2 = column2;
    }

    @Override
    public String mkSql() {
        TableInfo tableInfo1 = TableInfoHelper.getTableInfo(entity1.getClass());
        TableInfo tableInfo2 = TableInfoHelper.getTableInfo(entity2.getClass());
        return String.format(" select a.*, b.* from %s as a %s %s as b on a.%s = b.%s ",
                tableInfo1.getTableName(), joinMode.getValue(), tableInfo2.getTableName(), column1, column2);
    }

    @Override
    public <R> QueryWrapper<R> mkQueryWrapper() {
        QueryWrapper<R> queryWrapper = super.mkQueryWrapper("a", entity1, null);
        return super.mkQueryWrapper("b", entity2, queryWrapper);
    }

    @Override
    public <R> QueryWrapper<R> setOrder(QueryWrapper<R> queryWrapper, OrderMode orderMode) {
        return queryWrapper.orderBy(orderMode != null && orderMode.getColumn() != null, orderMode.isAsc(),
                "a." + ColumnContext.parse(orderMode.getColumn()));
    }
}
