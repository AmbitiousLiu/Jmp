package ambitiousliu.controller;

import ambitiousliu.entity.Name;
import ambitiousliu.entity.Test;
import ambitiousliu.entity.TestName;
import ambitiousliu.service.TestNameService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.github.ambitiousliu.jmp.constant.JoinMode;
import io.github.ambitiousliu.jmp.constant.OrderMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TestController {

    @Autowired
    TestNameService service;

    @GetMapping("/test")
    public List<TestName> get() {
        Page<TestName> testNamePage = service.joinByPage(new Test(), JoinMode.LEFT_JOIN, new Name(), Test::getName, Name::getName, new Page<>(1, 10), OrderMode.DESC.by(Test::getName));
        return testNamePage.getRecords();
    }
}
