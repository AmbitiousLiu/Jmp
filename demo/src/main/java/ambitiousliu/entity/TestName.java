package ambitiousliu.entity;

public class TestName {
    Integer id;
    String name;
    Integer age;
    String address;

    public String getAddress() {
        return address;
    }

    public TestName setAddress(String address) {
        this.address = address;
        return this;
    }

    public Integer getId() {
        return id;
    }

    public TestName setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public TestName setName(String name) {
        this.name = name;
        return this;
    }

    public Integer getAge() {
        return age;
    }

    public TestName setAge(Integer age) {
        this.age = age;
        return this;
    }
}
