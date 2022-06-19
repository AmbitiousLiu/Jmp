package io.github.ambitiousliu.jmp.constant;

/**
 * @author ambitious liu
 * @since 2022-06-19
 */
public enum JoinMode {
    LEFT_JOIN("left join"),
    RIGHT_JOIN("right join"),
    INNER_JOIN("inner join"),
    OUT_JOIN("out join");

    String value;

    JoinMode(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
