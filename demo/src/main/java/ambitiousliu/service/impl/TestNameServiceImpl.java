package ambitiousliu.service.impl;

import ambitiousliu.dao.TestNameMapper;
import ambitiousliu.entity.TestName;
import ambitiousliu.service.TestNameService;
import io.github.ambitiousliu.jmp.service.impl.JmpJoinServiceImpl;
import io.github.ambitiousliu.jmp.service.impl.JmpServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class TestNameServiceImpl extends JmpJoinServiceImpl<TestNameMapper, TestName> implements TestNameService {
}
