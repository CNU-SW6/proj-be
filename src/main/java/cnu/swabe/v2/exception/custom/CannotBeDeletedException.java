package cnu.swabe.v2.exception.custom;

import cnu.swabe.v2.exception.ExceptionCode;

public class CannotBeDeletedException extends RuntimeException {
    ExceptionCode exceptionCode;

    public CannotBeDeletedException() {
    }

    public CannotBeDeletedException(ExceptionCode exceptionCode) {
        this.exceptionCode = exceptionCode;
    }

    public ExceptionCode getErrorCode() {
        return exceptionCode;
    }
}
