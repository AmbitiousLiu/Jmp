package ambitiousliu.controller;

import ambitiousliu.entity.Table2;
import ambitiousliu.entity.Table1;
import ambitiousliu.entity.Table3;
import ambitiousliu.entity.TableVo;
import ambitiousliu.service.TableVoService;
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
    TableVoService service;

    @GetMapping("/test")
    public List<TableVo> get() {
        Page<TableVo> testNamePage = service.join(new Table1(), JoinMode.LEFT_JOIN, new Table2(), Table1::getName, Table2::getName, new Page<>(1, 10), OrderMode.DESC.by(Table1::getName));
        Page<TableVo> tableVoPage = service.join(new Table1(), JoinMode.LEFT_JOIN, new Table3(), Table1::getId, Table3::getId, new Page<>(1, 10));

        return testNamePage.getRecords();
    }
}
