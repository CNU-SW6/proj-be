package cnu.swabe.v1.exception;

import cnu.swabe.v1.exception.custom.NicknameDuplicatedException;
import cnu.swabe.v1.exception.custom.WrongLengthUserInfoException;
import cnu.swabe.v1.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class MyExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NicknameDuplicatedException.class)
    protected ErrorResponse handleNicknameDuplicatedException() {
        log.error("!!! error log={}", ExceptionCode.EXIST_VALUE.getMessage());
        return new ErrorResponse(ExceptionCode.EXIST_VALUE);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(WrongLengthUserInfoException.class)
    protected ErrorResponse handleWrongLengthUserInfoException(WrongLengthUserInfoException ex) {
        log.error("!!! error log={}", ex.getErrorCode().getMessage());
        return new ErrorResponse(ex.getErrorCode());
    }
}
