package cnu.swabe.v0.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    WRONG_LENGTH_USER_VALUE(400, "잘못된 입력값"),
    EXIST_VALUE(400, "존재하는 값");

    private int statusCode;
    private String message;

    ErrorCode(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }
}
