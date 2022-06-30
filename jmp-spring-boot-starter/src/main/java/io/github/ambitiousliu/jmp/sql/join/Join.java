package io.github.ambitiousliu.jmp.sql.join;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import io.github.ambitiousliu.jmp.constant.JoinMode;
import io.github.ambitiousliu.jmp.constant.OrderMode;
import io.github.ambitiousliu.jmp.context.ColumnContext;
import org.springframework.util.Assert;

/**
 * @author ambitious liu
 * @since 2022-06-19
 */
public class Join<M, N> extends AbstractJoin {

    M entity1;
    JoinMode joinMode;
    N entity2;
    SFunction<M, ?> column1;
    SFunction<N, ?> column2;

    public Join(M entity1, JoinMode joinMode, N entity2, SFunction<M, ?> column1, SFunction<N, ?> column2) {
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
        String c1 = ColumnContext.parse(column1);
        String c2 = ColumnContext.parse(column2);
        return String.format(" select a.*, b.* from %s as a %s %s as b on a.%s = b.%s ",
                tableInfo1.getTableName(), joinMode.getValue(), tableInfo2.getTableName(), c1, c2);
    }

    @Override
    public <R> QueryWrapper<R> mkQueryWrapper() {
        QueryWrapper<R> queryWrapper = super.mkQueryWrapper("a", entity1, null);
        return super.mkQueryWrapper("b", entity2, queryWrapper);
    }

    @Override
    public <R> QueryWrapper<R> setOrder(QueryWrapper<R> queryWrapper, OrderMode orderMode) {
        Assert.notNull(orderMode, "order mode can not be null");
        Assert.notNull(orderMode.getColumn(), "order column can not be null");
        return queryWrapper.orderBy(true, orderMode.isAsc(),
                // todo add b.
                "a." + ColumnContext.parse(orderMode.getColumn()));
    }
}
