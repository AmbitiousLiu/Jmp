package ambitiousliu.dao;

import ambitiousliu.entity.TableVo;
import io.github.ambitiousliu.jmp.mapper.JmpJoinMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TableVoMapper extends JmpJoinMapper<TableVo> {
}
