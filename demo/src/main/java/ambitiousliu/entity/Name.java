package ambitiousliu.entity;

import io.github.ambitiousliu.jmp.annotation.JmpId;

public class Name {
    @JmpId
    String name;
    Integer age;
    String address;

    public String getName() {
        return name;
    }

    public Name setName(String name) {
        this.name = name;
        return this;
    }

    public Integer getAge() {
        return age;
    }

    public Name setAge(Integer age) {
        this.age = age;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public Name setAddress(String address) {
        this.address = address;
        return this;
    }
}
