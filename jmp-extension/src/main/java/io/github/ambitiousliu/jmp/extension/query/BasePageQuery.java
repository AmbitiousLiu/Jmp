package io.github.ambitiousliu.jmp.extension.query;

import com.baomidou.mybatisplus.core.conditions.AbstractWrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.javatuples.Pair;

/**
 * @author ambitious liu
 * @since 2022/7/17
 */
public abstract class BasePageQuery<T, Q extends Query<T>> implements PageQuery<T> {

    Q query;

    PageEntity<T> page;

    public BasePageQuery<T, Q> setQuery(Q query) {
        this.query = query;
        return this;
    }

    public Q getQuery() {
        return query;
    }

    public PageEntity<T> getPage() {
        return page;
    }

    public BasePageQuery<T, Q> setPage(PageEntity<T> page) {
        this.page = page;
        return this;
    }

    @Override
    public Pair<AbstractWrapper<T, ?, ?>, Page<T>> mkPageQuery() {
        return Pair.with(
            query.mkQuery(), page.mkPage()
        );
    }
}
