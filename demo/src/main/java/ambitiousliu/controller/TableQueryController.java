package ambitiousliu.controller;

import ambitiousliu.entity.Table1;
import ambitiousliu.query.Table1Query;
import ambitiousliu.service.Table1Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author ambitious liu
 * @since 2022/9/18
 */
@RestController
@RequestMapping("/")
public class TableQueryController {

    @Autowired
    Table1Service service;

    @PostMapping("query")
    public List<Table1> queryTest(@RequestBody Table1Query query) {
        return service.list(query.mkQuery());
    }
}
