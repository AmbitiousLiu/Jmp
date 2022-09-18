package io.github.ambitiousliu.jmp.extension.query;

import com.baomidou.mybatisplus.core.conditions.AbstractWrapper;

/**
 * @author ambitious liu
 * @since 2022/7/17
 */
public interface Query<T> {
    AbstractWrapper<T, ?, ?> mkQuery();
}
