package cnu.swabe.v2.exception;

import cnu.swabe.v2.exception.custom.*;
import cnu.swabe.v2.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class MyExceptionHandler {
    @ExceptionHandler(DuplicatedInfoException.class)
    protected ErrorResponse handleDuplicatedInfoException(DuplicatedInfoException ex) {
        log.error("error log={}", ex.getErrorCode().getMessage());
        return new ErrorResponse(ex.getErrorCode());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(WrongUserFormException.class)
    protected ErrorResponse handleWrongUserFormException(WrongUserFormException ex) {
        log.error("error log={}", ex.getErrorCode().getMessage());
        return new ErrorResponse(ex.getErrorCode());
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(WrongInfoException.class)
    protected ErrorResponse handlerWrongInfoAccessException(WrongInfoException ex){
        log.error("error log={}", ex.getErrorCode().getMessage());
        return new ErrorResponse(ex.getErrorCode());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotExistException.class)
    protected ErrorResponse handlePostNotExistException(NotExistException ex) {
        log.error("error log={}", ex.getErrorCode().getMessage());
        return new ErrorResponse(ex.getErrorCode());
    }

    @ExceptionHandler(WrongPostFormException.class)
    protected ErrorResponse handleWrongPostFormException(WrongPostFormException ex) {
        log.error("error log={}", ex.getErrorCode().getMessage());
        return new ErrorResponse(ex.getErrorCode());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CannotBeDeletedException.class)
    protected ErrorResponse handleCannotBeDeletedException(CannotBeDeletedException ex) {
        log.error("error log={}", ex.getErrorCode().getMessage());
        return new ErrorResponse(ex.getErrorCode());
    }
}
