package io.github.ambitiousliu.jmp.sql.join.tp;

import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import io.github.ambitiousliu.jmp.conditions.LambdaJoinWrapper;
import io.github.ambitiousliu.jmp.constant.JoinMode;
import io.github.ambitiousliu.jmp.context.ColumnContext;
import io.github.ambitiousliu.jmp.sql.join.AbstractJoin;
import org.javatuples.Pair;
import org.javatuples.Triplet;

/**
 * @author ambitious liu
 * @since 2022-06-19
 */
public class LambdaJoin3<X, Y, Z, R> extends AbstractJoin<R> {

    final LambdaJoinWrapper<X> wrapper1;
    JoinMode joinMode1;
    final LambdaJoinWrapper<Y> wrapper2;
    SFunction<X, ?> column1;
    SFunction<Y, ?> column2;
    JoinMode joinMode2;
    final LambdaJoinWrapper<Z> wrapper3;
    SFunction<Y, ?> column3;
    SFunction<Z, ?> column4;
    LambdaJoinWrapper<R> targetWrapper;

    public LambdaJoin3(X entity1, JoinMode joinMode1, Y entity2, SFunction<X, ?> column1, SFunction<Y, ?> column2,
                       JoinMode joinMode2, Z entity3, SFunction<Y, ?> column3, SFunction<Z, ?> column4) {
        targetWrapper = new LambdaJoinWrapper<>("", null);
        wrapper1 = new LambdaJoinWrapper<>("a.", targetWrapper);
        wrapper1.setJoinEntity(entity1);
        this.joinMode1 = joinMode1;
        wrapper2 = new LambdaJoinWrapper<>("b.", targetWrapper);
        wrapper2.setJoinEntity(entity2);
        this.column1 = column1;
        this.column2 = column2;

        this.joinMode2 = joinMode2;
        wrapper3 = new LambdaJoinWrapper<>("c.", targetWrapper);
        wrapper3.setJoinEntity(entity3);
        this.column3 = column3;
        this.column4 = column4;
    }

    public Triplet<LambdaJoinWrapper<X>, LambdaJoinWrapper<Y>, LambdaJoinWrapper<Z>> lambdaJoinWrapper() {
        return Triplet.with(wrapper1, wrapper2, wrapper3);
    }

    @Override
    protected String mkSql() {
        Class<?> class1 = wrapper1.getJoinEntity().getClass();
        TableInfo tableInfo1 = TableInfoHelper.getTableInfo(class1);

        Class<?> class2 = wrapper2.getJoinEntity().getClass();
        TableInfo tableInfo2 = TableInfoHelper.getTableInfo(class2);

        Class<?> class3 = wrapper3.getJoinEntity().getClass();
        TableInfo tableInfo3 = TableInfoHelper.getTableInfo(class3);

        String c1 = ColumnContext.parse(column1);
        String c2 = ColumnContext.parse(column2);
        String c3 = ColumnContext.parse(column3);
        String c4 = ColumnContext.parse(column4);

        assert tableInfo1 != null;
        assert tableInfo2 != null;
        assert tableInfo3 != null;
        return String.format(" select a.*, b.*, c.* from %s as a %s %s as b on a.%s = b.%s %s %s as c on b.%s = c.%s ",
                tableInfo1.getTableName(), joinMode1.getValue(), tableInfo2.getTableName(), c1, c2,
                joinMode2.getValue(), tableInfo3.getTableName(), c3, c4);
    }

    @Override
    protected LambdaJoinWrapper<R> mkWrapper() {
        targetWrapper.mergeAndPrepare(wrapper1, wrapper2, wrapper3);
        return targetWrapper;
    }
}
