package cnu.swabe.v0.exception.custom;

import cnu.swabe.v0.exception.ErrorCode;

public class WrongLengthUserInfoException extends RuntimeException {
    ErrorCode errorCode;

    public WrongLengthUserInfoException() {
    }

    public WrongLengthUserInfoException(String message) {
        super(message);
    }

    public WrongLengthUserInfoException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
