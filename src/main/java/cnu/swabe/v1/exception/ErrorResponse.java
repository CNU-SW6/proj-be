package cnu.swabe.v1.exception;


import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ErrorResponse {
    private int statusCode;
    private String message;

    public ErrorResponse(ErrorCode errorCode) {
        this.statusCode = errorCode.getStatusCode();
        this.message = errorCode.getMessage();
    }
}
