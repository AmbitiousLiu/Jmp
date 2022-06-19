package io.github.ambitiousliu.jmp.exception;

/**
 * @author ambitious liu
 * @since 2022-06-18
 */
public class NotSupportException extends JmpException {
    public NotSupportException() {
        super("not support or deprecated");
    }

    public NotSupportException(String message) {
        super(message);
    }

    public NotSupportException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotSupportException(Throwable cause) {
        super(cause);
    }

    public NotSupportException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
