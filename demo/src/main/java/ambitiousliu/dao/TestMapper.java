package ambitiousliu.dao;

import ambitiousliu.entity.Test;
import io.github.ambitiousliu.jmp.mapper.JmpMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TestMapper extends JmpMapper<Test> {
}
