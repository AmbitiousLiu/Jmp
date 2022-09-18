package io.github.ambitiousliu.jmp.extension.query;

import com.baomidou.mybatisplus.core.conditions.AbstractWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.javatuples.Pair;

/**
 * @author ambitious liu
 * @since 2022/7/18
 */
public interface PageQuery<T> {

    Pair<AbstractWrapper<T, ?, ?>, Page<T>> mkPageQuery();
}
