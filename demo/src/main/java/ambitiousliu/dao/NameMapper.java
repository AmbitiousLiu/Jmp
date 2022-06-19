package ambitiousliu.dao;

import ambitiousliu.entity.Name;
import io.github.ambitiousliu.jmp.mapper.JmpMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface NameMapper extends JmpMapper<Name> {
}
