package cnu.swabe.v2.response;

import cnu.swabe.v2.exception.ExceptionCode;
import lombok.Getter;

@Getter
public class ErrorResponse {
    private boolean error;
    private String message;

    public ErrorResponse(ExceptionCode exceptionCode) {
        this.error = true;
        this.message = exceptionCode.getMessage();
    }
}
