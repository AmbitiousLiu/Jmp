package ambitiousliu.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.github.ambitiousliu.jmp.annotation.JmpId;

@TableName("test")
public class Table1 {

    @JmpId
    Integer id;

    @JmpId("name")
    String name;

    Integer age;

    public Integer getId() {
        return id;
    }

    public Table1 setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Table1 setName(String name) {
        this.name = name;
        return this;
    }

    public Integer getAge() {
        return age;
    }

    public Table1 setAge(Integer age) {
        this.age = age;
        return this;
    }
}
