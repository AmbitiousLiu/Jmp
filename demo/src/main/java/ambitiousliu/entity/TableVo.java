package ambitiousliu.entity;

public class TableVo extends Table1 {

    // table2
    String address;

    // table3
    String code;

    public String getAddress() {
        return address;
    }

    public TableVo setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getCode() {
        return code;
    }

    public TableVo setCode(String code) {
        this.code = code;
        return this;
    }
}
