package io.github.ambitiousliu.jmp.sql.join;

import io.github.ambitiousliu.jmp.conditions.AbstractJoinWrapper;
import io.github.ambitiousliu.jmp.conditions.JoinWrapper;

/**
 * @author ambitious liu
 * @since 2022/7/10
 */
public abstract class AbstractJoin<R> {
    String sql;
    AbstractJoinWrapper<R, ?, ?> wrapper;

    public void prepare() {
        sql = mkSql();
        wrapper = mkWrapper();
    }

    public String getSql() {
        return sql;
    }

    public AbstractJoinWrapper<R, ?, ?> getTargetWrapper() {
        return wrapper;
    }

    protected abstract String mkSql();

    protected abstract AbstractJoinWrapper<R, ?, ?> mkWrapper();
}
