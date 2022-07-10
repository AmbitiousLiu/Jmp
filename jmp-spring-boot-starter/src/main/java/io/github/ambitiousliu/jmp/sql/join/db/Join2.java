package io.github.ambitiousliu.jmp.sql.join.db;

import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import io.github.ambitiousliu.jmp.conditions.JoinWrapper;
import io.github.ambitiousliu.jmp.constant.JoinMode;
import io.github.ambitiousliu.jmp.context.ColumnContext;
import io.github.ambitiousliu.jmp.sql.join.AbstractJoin;
import org.javatuples.Pair;

/**
 * @author ambitious liu
 * @since 2022-06-19
 */
public class Join2<M, N, R> extends AbstractJoin<R> {

    final JoinWrapper<M> wrapper1;
    JoinMode joinMode;
    final JoinWrapper<N> wrapper2;
    SFunction<M, ?> column1;
    SFunction<N, ?> column2;
    JoinWrapper<R> targetWrapper;

    public Join2(M entity1, JoinMode joinMode, N entity2, SFunction<M, ?> column1, SFunction<N, ?> column2) {
        targetWrapper = new JoinWrapper<>("", null);
        wrapper1 = new JoinWrapper<>("a.", targetWrapper);
        wrapper1.setJoinEntity(entity1);
        this.joinMode = joinMode;
        wrapper2 = new JoinWrapper<>("b.", targetWrapper);
        wrapper2.setJoinEntity(entity2);
        this.column1 = column1;
        this.column2 = column2;
    }

    public Pair<JoinWrapper<M>, JoinWrapper<N>> joinWrapper() {
        return Pair.with(wrapper1, wrapper2);
    }

    @Override
    protected String mkSql() {
        Class<?> class1 = wrapper1.getJoinEntity().getClass();
        TableInfo tableInfo1 = TableInfoHelper.getTableInfo(class1);

        Class<?> class2 = wrapper2.getJoinEntity().getClass();
        TableInfo tableInfo2 = TableInfoHelper.getTableInfo(class2);

        String c1 = ColumnContext.parse(column1);
        String c2 = ColumnContext.parse(column2);

        assert tableInfo1 != null;
        assert tableInfo2 != null;
        return String.format(" select a.*, b.* from %s as a %s %s as b on a.%s = b.%s ",
                tableInfo1.getTableName(), joinMode.getValue(), tableInfo2.getTableName(), c1, c2);
    }

    @Override
    protected JoinWrapper<R> mkWrapper() {
        targetWrapper.mergeAndPrepare(wrapper1, wrapper2);
        return targetWrapper;
    }
}
