package cnu.swabe.v2.exception.custom;

import cnu.swabe.v2.exception.ExceptionCode;

public class DuplicatedInfoException extends IllegalArgumentException {
    ExceptionCode exceptionCode;

    public DuplicatedInfoException() {
    }

    public DuplicatedInfoException(ExceptionCode exceptionCode) {
        this.exceptionCode = exceptionCode;
    }

    public ExceptionCode getErrorCode() {
        return exceptionCode;
    }
}
