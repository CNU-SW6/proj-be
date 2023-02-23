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
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(DuplicatedInfoException.class)
    protected ErrorResponse handleDuplicatedInfoException(DuplicatedInfoException ex) {
        log.error("error log={}", ex.getErrorCode().getMessage());
        return new ErrorResponse(ex.getErrorCode());
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(WrongUserFormException.class)
    protected ErrorResponse handleWrongUserFormException(WrongUserFormException ex) {
        log.error("error log={}", ex.getErrorCode().getMessage());
        return new ErrorResponse(ex.getErrorCode());
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(WrongPostFormException.class)
    protected ErrorResponse handleWrongPostFormException(WrongPostFormException ex) {
        log.error("error log={}", ex.getErrorCode().getMessage());
        return new ErrorResponse(ex.getErrorCode());
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(WrongInfoException.class)
    protected ErrorResponse handlerWrongInfoAccessException(WrongInfoException ex){
        log.error("error log={}", ex.getErrorCode().getMessage());
        return new ErrorResponse(ex.getErrorCode());
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(NotExistException.class)
    protected ErrorResponse handlePostNotExistException(NotExistException ex) {
        log.error("error log={}", ex.getErrorCode().getMessage());
        return new ErrorResponse(ex.getErrorCode());
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(CannotBeDeletedException.class)
    protected ErrorResponse handleCannotBeDeletedException(CannotBeDeletedException ex) {
        log.error("error log={}", ex.getErrorCode().getMessage());
        return new ErrorResponse(ex.getErrorCode());
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(S3Exception.class)
    protected ErrorResponse handleS3Exception(S3Exception ex) {
        log.error("error log={}", ex.getErrorCode().getMessage());
        return new ErrorResponse(ex.getErrorCode());
    }
}
