package io.github.ambitiousliu.jmp.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.github.ambitiousliu.jmp.constant.JoinMode;
import io.github.ambitiousliu.jmp.constant.OrderMode;
import io.github.ambitiousliu.jmp.context.ColumnContext;
import io.github.ambitiousliu.jmp.mapper.JmpJoinMapper;
import io.github.ambitiousliu.jmp.sql.join.Join;

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
     * @param entity1 表1
     * @param joinMode join模式 {@link JoinMode}
     * @param entity2 表2
     * @param column1 表1的连接字段
     * @param column2 表2的连接字段
     * @param <M> 表1的实体类
     * @param <N> 表2的实体类
     * @return 对象列表
     */
    default <M, N> List<T> joinAll(M entity1, JoinMode joinMode, N entity2, SFunction<M, ?> column1, SFunction<N, ?> column2) {
        Join<M, N> join = new Join<>(entity1, joinMode, entity2, column1, column2);
        return getBaseMapper().joinAll(join.mkSql(), join.mkQueryWrapper());
    }

    /**
     * 联表查所有并排序
     *
     * @param entity1 表1
     * @param joinMode join模式 {@link JoinMode}
     * @param entity2 表2
     * @param column1 表1的连接字段
     * @param column2 表2的连接字段
     * @param orderMode 排序对象, 仅支持M类型(表1)的一个字段排序 {@link OrderMode}
     * @param <M> 表1的实体类
     * @param <N> 表2的实体类
     * @return 对象列表
     */
    default <M, N> List<T> joinAll(M entity1, JoinMode joinMode, N entity2, SFunction<M, ?> column1, SFunction<N, ?> column2, OrderMode orderMode) {
        Join<M, N> join = new Join<>(entity1, joinMode, entity2, column1, column2);
        QueryWrapper<T> queryWrapper = join.setOrder(join.mkQueryWrapper(), orderMode);
        return getBaseMapper().joinAll(join.mkSql(), queryWrapper);
    }

    /**
     * 分页联表查询
     *
     * @param entity1 表1
     * @param joinMode join模式 {@link JoinMode}
     * @param entity2 表2
     * @param column1 表1的连接字段
     * @param column2 表2的连接字段
     * @param page 分页对象 {@link Page}
     * @param <M> 表1的实体类
     * @param <N> 表2的实体类
     * @return 分页数据
     */
    default <M, N> Page<T> joinByPage(M entity1, JoinMode joinMode, N entity2, SFunction<M, ?> column1, SFunction<N, ?> column2, Page<T> page) {
        Join<M, N> join = new Join<>(entity1, joinMode, entity2, column1, column2);
        return getBaseMapper().joinByPage(join.mkSql(), join.mkQueryWrapper(), page);
    }

    /**
     * 分页联表排序查询
     *
     * @param entity1 表1
     * @param joinMode join模式 {@link JoinMode}
     * @param entity2 表2
     * @param column1 表1的连接字段
     * @param column2 表2的连接字段
     * @param page 分页对象 {@link Page}
     * @param orderMode 排序对象 {@link OrderMode}
     * @param <M> 表1的实体类
     * @param <N> 表2的实体类
     * @return 分页数据
     */
    default <M, N> Page<T> joinByPage(M entity1, JoinMode joinMode, N entity2, SFunction<M, ?> column1, SFunction<N, ?> column2, Page<T> page, OrderMode orderMode) {
        Join<M, N> join = new Join<>(entity1, joinMode, entity2, column1, column2);
        QueryWrapper<T> queryWrapper = join.setOrder(join.mkQueryWrapper(), orderMode);
        return getBaseMapper().joinByPage(join.mkSql(), queryWrapper, page);
    }
}
