package ambitiousliu.entity;

import io.github.ambitiousliu.jmp.annotation.JmpId;


public class Test {

    @JmpId
    Integer id;

    @JmpId("name")
    String name;

    Integer age;

    public Integer getId() {
        return id;
    }

    public Test setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Test setName(String name) {
        this.name = name;
        return this;
    }

    public Integer getAge() {
        return age;
    }

    public Test setAge(Integer age) {
        this.age = age;
        return this;
    }
}
