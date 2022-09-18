package io.github.ambitiousliu.jmp.extension.query;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * @author ambitious liu
 * @since 2022/7/17
 */
public class PageEntity<T> {

    Long current;

    Long size;

    public Page<T> mkPage() {
        return new Page<>(current, size);
    }
}
