package ambitiousliu.service.impl;

import ambitiousliu.dao.Table1Mapper;
import ambitiousliu.entity.Table1;
import ambitiousliu.service.Table1Service;
import io.github.ambitiousliu.jmp.service.impl.JmpServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class Table1ServiceImpl extends JmpServiceImpl<Table1Mapper, Table1> implements Table1Service {

}
