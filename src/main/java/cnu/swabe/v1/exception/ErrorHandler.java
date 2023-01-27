package cnu.swabe.v1.exception;

import cnu.swabe.v1.exception.custom.NicknameDuplicatedException;
import cnu.swabe.v1.exception.custom.WrongLengthUserInfoException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(NicknameDuplicatedException.class)
    protected ErrorResponse handleNicknameDuplicatedException() {
        log.error("??? error log={}", ErrorCode.EXIST_VALUE.getMessage());
        return new ErrorResponse(ErrorCode.EXIST_VALUE);
    }

    @ExceptionHandler(WrongLengthUserInfoException.class)
    protected ErrorResponse handleWrongLengthUserInfoException(WrongLengthUserInfoException ex) {
        log.error("??? error log={}", ex.getErrorCode().getMessage());
        return new ErrorResponse(ex.getErrorCode());
    }
}
