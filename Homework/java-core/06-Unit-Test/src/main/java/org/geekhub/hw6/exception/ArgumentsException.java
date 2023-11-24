package org.geekhub.hw6.exception;

public class ArgumentsException extends RuntimeException {
    public ArgumentsException(String message) {
        super(message);
    }

    public ArgumentsException(String message, Throwable cause) {
        super(message, cause);
    }
}
