package cnu.swabe.v2.exception.custom;

import cnu.swabe.v2.exception.ExceptionCode;

public class WrongLengthUserInfoException extends RuntimeException {
    ExceptionCode errorCode;

    public WrongLengthUserInfoException() {
    }

    public WrongLengthUserInfoException(String message) {
        super(message);
    }

    public WrongLengthUserInfoException(ExceptionCode errorCode) {
        this.errorCode = errorCode;
    }

    public ExceptionCode getErrorCode() {
        return errorCode;
    }
}
