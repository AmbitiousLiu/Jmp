package ambitiousliu.service.impl;

import ambitiousliu.dao.TestMapper;
import ambitiousliu.entity.Test;
import ambitiousliu.service.TestService;
import io.github.ambitiousliu.jmp.service.impl.JmpServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class TestServiceImpl extends JmpServiceImpl<TestMapper, Test> implements TestService {

}
