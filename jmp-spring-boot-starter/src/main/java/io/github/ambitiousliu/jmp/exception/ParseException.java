package io.github.ambitiousliu.jmp.exception;

/**
 * @author ambitious liu
 * @since 2022-06-18
 */
public class ParseException extends JmpException {
    public ParseException() {
        super("parse error");
    }

    public ParseException(String message) {
        super(message);
    }

    public ParseException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParseException(Throwable cause) {
        super(cause);
    }

    public ParseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
