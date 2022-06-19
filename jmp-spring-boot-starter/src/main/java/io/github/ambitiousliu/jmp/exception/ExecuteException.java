package io.github.ambitiousliu.jmp.exception;

/**
 * @author ambitious liu
 * @since 2022-06-18
 */
public class ExecuteException extends JmpException {
    public ExecuteException() {
        super("execute error");
    }

    public ExecuteException(String message) {
        super(message);
    }

    public ExecuteException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExecuteException(Throwable cause) {
        super(cause);
    }

    public ExecuteException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
