package ambitiousliu.dao;

import ambitiousliu.entity.TestName;
import io.github.ambitiousliu.jmp.mapper.JmpJoinMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TestNameMapper extends JmpJoinMapper<TestName> {
}
