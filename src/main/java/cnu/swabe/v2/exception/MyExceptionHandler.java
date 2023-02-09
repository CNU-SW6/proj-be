package cnu.swabe.v2.exception;

import cnu.swabe.v2.exception.custom.IdDuplicatedException;
import cnu.swabe.v2.exception.custom.NicknameDuplicatedException;
import cnu.swabe.v2.exception.custom.WrongInfoAccessException;
import cnu.swabe.v2.exception.custom.PostNotExistException;
import cnu.swabe.v2.exception.custom.WrongLengthUserInfoException;
import cnu.swabe.v2.exception.custom.CannotBeDeletedException;
import cnu.swabe.v2.exception.custom.DuplicatedInfoException;
import cnu.swabe.v2.exception.custom.WrongPostFormException;
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
    @ExceptionHandler(NicknameDuplicatedException.class)
    protected ErrorResponse handleNicknameDuplicatedException() {
        log.error("!!! error log={}", ExceptionCode.EXIST_VALUE.getMessage());
        return new ErrorResponse(ExceptionCode.EXIST_VALUE);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(WrongLengthUserInfoException.class)
    protected ErrorResponse handleWrongLengthUserInfoException(WrongLengthUserInfoException ex) {
        log.error("!!! error log={}", ex.getErrorCode().getMessage());
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

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IdDuplicatedException.class)
    protected ErrorResponse handleIdDuplicatedException(){
        log.error("!!! error log=()", ExceptionCode.EXIST_VALUE.getMessage());
        return new ErrorResponse(ExceptionCode.EXIST_VALUE);
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(WrongInfoAccessException.class)
    protected ErrorResponse handlerWrongInfoAccessException(WrongInfoAccessException ex){
        return new ErrorResponse(ex.getErrorCode());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(PostNotExistException.class)
    protected ErrorResponse handlePostNotExistException(PostNotExistException ex) {
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
