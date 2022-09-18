package ambitiousliu.query;

import ambitiousliu.entity.Table1;
import com.baomidou.mybatisplus.core.conditions.AbstractWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.github.ambitiousliu.jmp.extension.annotation.JmpMultiple;
import io.github.ambitiousliu.jmp.extension.query.MultipleQuery;
import io.github.ambitiousliu.jutil.code.BeanUtil;

/**
 * @author ambitious liu
 * @since 2022/9/18
 */
public class Table1Query extends MultipleQuery<Table1> {

    Integer id;

    @JmpMultiple
    String name;

    @JmpMultiple
    Integer age;

    public Integer getId() {
        return id;
    }

    public Table1Query setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Table1Query setName(String name) {
        this.name = name;
        return this;
    }

    public Integer getAge() {
        return age;
    }

    public Table1Query setAge(Integer age) {
        this.age = age;
        return this;
    }

    @Override
    public AbstractWrapper<Table1, ?, ?> mkQuery() {
        QueryWrapper<Table1> table1QueryWrapper = super.mkMultipleQuery();
        table1QueryWrapper.setEntity(BeanUtil.copyObject(this, new Table1()));
        return table1QueryWrapper;
    }
}
