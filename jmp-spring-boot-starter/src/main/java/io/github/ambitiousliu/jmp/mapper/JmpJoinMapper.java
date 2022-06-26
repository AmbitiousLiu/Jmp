package io.github.ambitiousliu.jmp.mapper;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.Mapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author ambitious liu
 * @since 2022/6/26
 */
public interface JmpJoinMapper<T> extends Mapper<T> {
    @Select("${sql} ${ew.customSqlSegment}")
    Page<T> joinByPage(@Param("sql") String selectSql, @Param(Constants.WRAPPER) Wrapper<T> queryWrapper, Page<T> page);

    @Select("${sql} ${ew.customSqlSegment}")
    List<T> joinAll(@Param("sql") String selectSql, @Param(Constants.WRAPPER) Wrapper<T> queryWrapper);
}
