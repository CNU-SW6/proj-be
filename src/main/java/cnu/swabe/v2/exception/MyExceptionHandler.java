package cnu.swabe.v2.exception;

import cnu.swabe.v2.exception.custom.DuplicatedInfoException;
import cnu.swabe.v2.exception.custom.WrongUserFormException;
import cnu.swabe.v2.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class MyExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DuplicatedInfoException.class)
    protected ErrorResponse handleDuplicatedInfoException(DuplicatedInfoException ex) {
        log.error("error log={}", ex.getErrorCode().getMessage());
        return new ErrorResponse(ex.getErrorCode());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(WrongUserFormException.class)
    protected ErrorResponse handleWrongLengthUserInfoException(WrongUserFormException ex) {
        log.error("error log={}", ex.getErrorCode().getMessage());
        return new ErrorResponse(ex.getErrorCode());
    }
}
