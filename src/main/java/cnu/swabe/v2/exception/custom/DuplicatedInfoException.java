package cnu.swabe.v2.exception.custom;

import cnu.swabe.v2.exception.ExceptionCode;

public class DuplicatedInfoException extends IllegalArgumentException {
    ExceptionCode errorCode;

    public DuplicatedInfoException() {
    }

    public DuplicatedInfoException(ExceptionCode errorCode) {
        this.errorCode = errorCode;
    }

    public ExceptionCode getErrorCode() {
        return errorCode;
    }
}
