package cnu.swabe.v1.response;


import cnu.swabe.v1.exception.ExceptionCode;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ErrorResponse {
    private boolean error;
    private String message;

    public ErrorResponse(ExceptionCode errorCode) {
        this.error = errorCode.isError();
        this.message = errorCode.getMessage();
    }
}
