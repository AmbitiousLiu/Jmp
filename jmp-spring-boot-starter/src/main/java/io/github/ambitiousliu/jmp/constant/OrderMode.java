package io.github.ambitiousliu.jmp.constant;

import com.baomidou.mybatisplus.core.toolkit.support.SFunction;

/**
 * @author ambitious liu
 * @since 2022-06-19
 */
public enum OrderMode {

    ASC(true),
    DESC(false);

    boolean asc;

    OrderMode(boolean asc) {
        this.asc = asc;
    }

    SFunction<?, ?> column;

    public boolean isAsc() {
        return asc;
    }

    public SFunction<?, ?> getColumn() {
        return column;
    }

    public <M> OrderMode by(SFunction<M, ?> column) {
        this.column = column;
        return this;
    }
}
