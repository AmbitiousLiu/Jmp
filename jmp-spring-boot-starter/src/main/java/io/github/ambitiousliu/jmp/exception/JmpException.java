package io.github.ambitiousliu.jmp.exception;

import io.github.ambitiousliu.jmp.constant.JmpConstant;

/**
 * @author ambitious liu
 * @since 2022-06-18
 */
public class JmpException extends RuntimeException {
    public JmpException() {
        super(JmpConstant.label);
    }

    public JmpException(String message) {
        super(JmpConstant.label + message);
    }

    public JmpException(String message, Throwable cause) {
        super(JmpConstant.label + message, cause);
    }

    public JmpException(Throwable cause) {
        super(JmpConstant.label, cause);
    }

    public JmpException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(JmpConstant.label + message, cause, enableSuppression, writableStackTrace);
    }
}
