package cnu.swabe.v2.exception.custom;

import cnu.swabe.v2.exception.ExceptionCode;

public class WrongPostFormException extends IllegalArgumentException {
    ExceptionCode exceptionCode;

    public WrongPostFormException() {
    }

    public WrongPostFormException(ExceptionCode exceptionCode) {
        this.exceptionCode = exceptionCode;
    }

    public ExceptionCode getErrorCode() {
        return exceptionCode;
    }

}
