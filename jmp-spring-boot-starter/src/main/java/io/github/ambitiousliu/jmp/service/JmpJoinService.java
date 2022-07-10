package io.github.ambitiousliu.jmp.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.github.ambitiousliu.jmp.mapper.JmpJoinMapper;
import io.github.ambitiousliu.jmp.sql.join.AbstractJoin;

import java.util.List;

/**
 * @author ambitious liu
 * @since 2022/6/26
 */
public interface JmpJoinService<T> {

    /**
     * 获取对应 entity 的 BaseMapper
     *
     * @return BaseMapper
     */
    JmpJoinMapper<T> getBaseMapper();

    /**
     * 联表查所有数据
     *
     * @param join 联表对象 {@link AbstractJoin}
     * @return 对象列表
     */
    default List<T> join(AbstractJoin<T> join) {
        join.prepare();
        return getBaseMapper().joinAll(join.getSql(), join.getTargetWrapper());
    }

    /**
     * 分页联表查询
     *
     * @param join 联表对象 {@link AbstractJoin}
     * @param page 分页对象 {@link Page}
     * @return 分页数据
     */
    default Page<T> join(AbstractJoin<T> join, Page<?> page) {
        join.prepare();
        return getBaseMapper().joinByPage(join.getSql(), join.getTargetWrapper(), page);
    }
}
