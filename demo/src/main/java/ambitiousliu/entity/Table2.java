package ambitiousliu.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.github.ambitiousliu.jmp.annotation.JmpId;

@TableName("name")
public class Table2 {
    @JmpId
    String name;
    Integer age;
    String address;

    public String getName() {
        return name;
    }

    public Table2 setName(String name) {
        this.name = name;
        return this;
    }

    public Integer getAge() {
        return age;
    }

    public Table2 setAge(Integer age) {
        this.age = age;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public Table2 setAddress(String address) {
        this.address = address;
        return this;
    }
}
