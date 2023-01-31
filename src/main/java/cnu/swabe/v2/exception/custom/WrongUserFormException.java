package cnu.swabe.v2.exception.custom;

import cnu.swabe.v2.exception.ExceptionCode;

public class WrongUserFormException extends RuntimeException {
    ExceptionCode errorCode;

    public WrongUserFormException() {
    }

    public WrongUserFormException(ExceptionCode errorCode) {
        this.errorCode = errorCode;
    }

    public ExceptionCode getErrorCode() {
        return errorCode;
    }
}
