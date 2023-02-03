package cnu.swabe.v2.exception.custom;

import cnu.swabe.v2.exception.ExceptionCode;

public class WrongUserFormException extends IllegalArgumentException {
    ExceptionCode exceptionCode;

    public WrongUserFormException() {
    }

    public WrongUserFormException(ExceptionCode exceptionCode) {
        this.exceptionCode = exceptionCode;
    }

    public ExceptionCode getErrorCode() {
        return exceptionCode;
    }
}
