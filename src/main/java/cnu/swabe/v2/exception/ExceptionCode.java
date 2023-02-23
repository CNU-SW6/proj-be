package cnu.swabe.v2.exception;

public enum ExceptionCode {
    WRONG_LENGTH_USER_ID(true, "잘못된 아이디 양식"),
    WRONG_LENGTH_USER_NICKNAME(true, "잘못된 닉네임 양식"),
    WRONG_LENGTH_USER_PW(true, "잘못된 비밀번호 양식"),
    NO_EXIST_POST(true, "존재하지 않는 게시물"),
    NO_EXIST_USER(true, "존재하지 않는 유저"),
    WRONG_USER_INFO(true, "잘못된 ID, PW 값 입력"),
    EXIST_USER_NICKNAME(true, "존재하는 닉네임"),
    EXIST_USER_ID(true, "존재하는 아이디"),
    NO_EXIST_POST_URL(true, "판매주소 없음"),
    NO_EXIST_COLOR(true, "색상 선택 안 됨"),
    DIFFERENCE_USER_NO_AND_POST_USER_NO(true, "삭제할 수 없음"),
    PROBLEM_BY_S3_SERVER(true, "S3 서버 문제"),
    PROBLEM_BY_S3_CLIENT(true, "S3 사용자 정보 문제");


    private boolean error;
    private String message;

    ExceptionCode(boolean error, String message) {
        this.error = error;
        this.message = message;
    }

    public boolean isError() {
        return error;
    }

    public String getMessage() {
        return message;
    }
}
