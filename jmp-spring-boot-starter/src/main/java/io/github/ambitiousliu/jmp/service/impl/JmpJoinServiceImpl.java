package io.github.ambitiousliu.jmp.service.impl;

import io.github.ambitiousliu.jmp.mapper.JmpJoinMapper;
import io.github.ambitiousliu.jmp.service.JmpJoinService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author ambitious liu
 * @since 2022/6/26
 */
public class JmpJoinServiceImpl<M extends JmpJoinMapper<T>, T> implements JmpJoinService<T> {

    @Autowired
    protected M baseMapper;

    @Override
    public M getBaseMapper() {
        return baseMapper;
    }

}
