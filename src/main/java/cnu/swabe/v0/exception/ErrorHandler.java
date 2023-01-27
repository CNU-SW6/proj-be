package cnu.swabe.v0.exception;

import cnu.swabe.v0.exception.custom.NicknameDuplicatedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(NicknameDuplicatedException.class)
    protected ErrorResponse handleNicknameDuplicatedException() {
        log.error("???error log={}", ErrorCode.EXIST_VALUE.getMessage());
        return new ErrorResponse(ErrorCode.EXIST_VALUE);
    }
}
