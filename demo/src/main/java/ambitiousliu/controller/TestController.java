package ambitiousliu.controller;

import ambitiousliu.entity.Table2;
import ambitiousliu.entity.Table1;
import ambitiousliu.entity.TableVo;
import ambitiousliu.service.TableVoService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.github.ambitiousliu.jmp.conditions.JoinWrapper;
import io.github.ambitiousliu.jmp.conditions.LambdaJoinWrapper;
import io.github.ambitiousliu.jmp.constant.JoinMode;
import io.github.ambitiousliu.jmp.sql.join.db.Join2;
import io.github.ambitiousliu.jmp.sql.join.db.LambdaJoin2;
import org.javatuples.Pair;
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
        Join2<Table1, Table2, TableVo> join2 = new Join2<>(
                new Table1(),
                JoinMode.LEFT_JOIN,
                new Table2(),
                Table1::getName,
                Table2::getName
        );
        Pair<JoinWrapper<Table1>, JoinWrapper<Table2>> objects = join2.joinWrapper();
        JoinWrapper<Table1> joinWrapper1 = objects.getValue0();
        joinWrapper1.eq("id", 0);
        joinWrapper1.orderByDesc("name");
        JoinWrapper<Table2> joinWrapper2 = objects.getValue1();
        joinWrapper2.eq("address", "0");
        Page<TableVo> testNamePage = service.join(join2, new Page<>(1, 10));

        return testNamePage.getRecords();
    }

    @GetMapping("/test2")
    public List<TableVo> get2() {
        LambdaJoin2<Table1, Table2, TableVo> lambdaJoin2 = new LambdaJoin2<>(
                new Table1(),
                JoinMode.LEFT_JOIN,
                new Table2(),
                Table1::getName,
                Table2::getName
        );
        Pair<LambdaJoinWrapper<Table1>, LambdaJoinWrapper<Table2>> objects = lambdaJoin2.lambdaJoinWrapper();
        LambdaJoinWrapper<Table1> lambda1 = objects.getValue0();
        lambda1.eq(Table1::getId, 0);
        lambda1.orderByDesc(Table1::getName);
        LambdaJoinWrapper<Table2> lambda2 = objects.getValue1();
        lambda2.eq(Table2::getAddress, "0");
        Page<TableVo> testNamePage = service.join(lambdaJoin2, new Page<>(1, 10));

        return testNamePage.getRecords();
    }
}
