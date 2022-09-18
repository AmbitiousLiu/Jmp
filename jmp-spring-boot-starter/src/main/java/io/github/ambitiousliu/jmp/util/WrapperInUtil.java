package io.github.ambitiousliu.jmp.util;

import com.baomidou.mybatisplus.core.conditions.AbstractWrapper;
import io.github.ambitiousliu.jmp.exception.ExecuteException;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * @author ambitious liu
 * @since 2022/8/6
 */
public class WrapperInUtil {
    private WrapperInUtil() {

    }

    /**
     * 推荐使用 isNull 更符合一般逻辑
     */
    public static <T, R extends AbstractWrapper<?, T, R>> R in(R wrapper, T column, Collection<?> collection) {
        return WrapperInUtil.in(wrapper, column, collection, WrapperInUtil::IS_NULL);
    }

    public static <T, R extends AbstractWrapper<?, T, R>> R in(R wrapper, T column, Collection<?> collection, BiConsumer<R, T> emptyProcessor) {
        if (CollectionUtils.isEmpty(collection)) {
            emptyProcessor.accept(wrapper, column);
        } else {
            wrapper.in(column, collection);
        }
        return wrapper;
    }
    public static <T, R extends AbstractWrapper<?, T, R>> R in(R wrapper, T column, Collection<?> collection, BiConsumer<R, T> nullProcessor, BiConsumer<R, T> emptyProcessor) {
        if (collection == null) {
            nullProcessor.accept(wrapper, column);
            return wrapper;
        }
        return in(wrapper, column, collection, emptyProcessor);
    }
    public static <T, R extends AbstractWrapper<?, T, R>> R in(R wrapper, T column, Collection<?> collection, BiConsumer<R, T> nullProcessor, Consumer<R> emptyProcessor) {
        if (collection == null) {
            nullProcessor.accept(wrapper, column);
            return wrapper;
        }
        return in(wrapper, column, collection, emptyProcessor);
    }
    public static <T, R extends AbstractWrapper<?, T, R>> R in(R wrapper, T column, Collection<?> collection, BiConsumer<R, T> nullProcessor, Runnable emptyProcessor) {
        if (collection == null) {
            nullProcessor.accept(wrapper, column);
            return wrapper;
        }
        return in(wrapper, column, collection, emptyProcessor);
    }

    public static <T, R extends AbstractWrapper<?, T, R>> R in(R wrapper, T column, Collection<?> collection, Consumer<R> emptyProcessor) {
        if (CollectionUtils.isEmpty(collection)) {
            emptyProcessor.accept(wrapper);
        } else {
            wrapper.in(column, collection);
        }
        return wrapper;
    }
    public static <T, R extends AbstractWrapper<?, T, R>> R in(R wrapper, T column, Collection<?> collection, Consumer<R> nullProcessor, BiConsumer<R, T> emptyProcessor) {
        if (collection == null) {
            nullProcessor.accept(wrapper);
            return wrapper;
        }
        return in(wrapper, column, collection, emptyProcessor);
    }
    public static <T, R extends AbstractWrapper<?, T, R>> R in(R wrapper, T column, Collection<?> collection, Consumer<R> nullProcessor, Consumer<R> emptyProcessor) {
        if (collection == null) {
            nullProcessor.accept(wrapper);
            return wrapper;
        }
        return in(wrapper, column, collection, emptyProcessor);
    }
    public static <T, R extends AbstractWrapper<?, T, R>> R in(R wrapper, T column, Collection<?> collection, Consumer<R> nullProcessor, Runnable emptyProcessor) {
        if (collection == null) {
            nullProcessor.accept(wrapper);
            return wrapper;
        }
        return in(wrapper, column, collection, emptyProcessor);
    }

    public static <T, R extends AbstractWrapper<?, T, R>> R in(R wrapper, T column, Collection<?> collection, Runnable emptyProcessor) {
        if (CollectionUtils.isEmpty(collection)) {
            emptyProcessor.run();
        } else {
            wrapper.in(column, collection);
        }
        return wrapper;
    }
    public static <T, R extends AbstractWrapper<?, T, R>> R in(R wrapper, T column, Collection<?> collection, Runnable nullProcessor, BiConsumer<R, T> emptyProcessor) {
        if (collection == null) {
            nullProcessor.run();
            return wrapper;
        }
        return in(wrapper, column, collection, emptyProcessor);
    }
    public static <T, R extends AbstractWrapper<?, T, R>> R in(R wrapper, T column, Collection<?> collection, Runnable nullProcessor, Consumer<R> emptyProcessor) {
        if (collection == null) {
            nullProcessor.run();
            return wrapper;
        }
        return in(wrapper, column, collection, emptyProcessor);
    }
    public static <T, R extends AbstractWrapper<?, T, R>> R in(R wrapper, T column, Collection<?> collection, Runnable nullProcessor, Runnable emptyProcessor) {
        if (collection == null) {
            nullProcessor.run();
            return wrapper;
        }
        return in(wrapper, column, collection, emptyProcessor);
    }


    /**
     * 预设的空列表处理器
     */
    public static <T, R extends AbstractWrapper<?, T, R>> void IS_NULL(R wrapper, T column) {
        wrapper.isNull(column);
    }

    public static <R extends AbstractWrapper<?, ?, R>> void LIMIT_ZERO(R wrapper) {
        wrapper.last("LIMIT 0");
    }

    public static void DO_NOTHING() {
    }

    public static void THROWS() {
        throw new ExecuteException("collection cannot be empty in wrapper 'in' query");
    }
}
