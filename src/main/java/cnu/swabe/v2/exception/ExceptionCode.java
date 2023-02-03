package cnu.swabe.v2.exception;

public enum ExceptionCode {
    WRONG_LENGTH_USER_ID(true, "잘못된 아이디 양식"),
    WRONG_LENGTH_USER_NICKNAME(true, "잘못된 닉네임 양식"),
    WRONG_LENGTH_USER_PW(true, "잘못된 비밀번호 양식"),
    EXIST_USER_NICKNAME(true, "존재하는 닉네임"),
    EXIST_USER_ID(true, "존재하는 아이디"),
    NO_EXIST_POST_URL(true, "판매주소 없음"),
    DIFFERENCE_USER_NO_AND_POST_USER_NO(true, "삭제할 수 없음");


    private boolean error;
    private String message;

    ExceptionCode(boolean error, String message) {
        this.error = error;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
