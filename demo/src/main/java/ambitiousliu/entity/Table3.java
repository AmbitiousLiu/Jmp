package ambitiousliu.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.github.ambitiousliu.jmp.annotation.JmpId;

/**
 * @author ambitious liu
 * @since 2022/6/29
 */
@TableName("table3")
public class Table3 {

    @JmpId
    Integer id;

    String code;

    public Integer getId() {
        return id;
    }

    public Table3 setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getCode() {
        return code;
    }

    public Table3 setCode(String code) {
        this.code = code;
        return this;
    }
}
