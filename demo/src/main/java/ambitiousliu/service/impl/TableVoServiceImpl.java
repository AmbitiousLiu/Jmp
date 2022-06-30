package ambitiousliu.service.impl;

import ambitiousliu.dao.TableVoMapper;
import ambitiousliu.entity.TableVo;
import ambitiousliu.service.TableVoService;
import io.github.ambitiousliu.jmp.service.impl.JmpJoinServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class TableVoServiceImpl extends JmpJoinServiceImpl<TableVoMapper, TableVo> implements TableVoService {
}
