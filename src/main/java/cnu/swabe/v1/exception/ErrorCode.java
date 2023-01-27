package cnu.swabe.v1.exception;

public enum ErrorCode {
    WRONG_LENGTH_USER_ID(400, "잘못된 아이디 양식"),
    WRONG_LENGTH_USER_NICKNAME(400, "잘못된 닉네임 양식"),
    WRONG_LENGTH_USER_PW(400, "잘못된 비밀번호 양식"),
    EXIST_VALUE(400, "존재하는 값.");

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
