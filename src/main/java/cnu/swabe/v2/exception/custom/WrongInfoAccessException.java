package cnu.swabe.v2.exception.custom;

import cnu.swabe.v2.exception.ExceptionCode;

public class WrongInfoAccessException extends RuntimeException {
    ExceptionCode errorCode;
    public WrongInfoAccessException(){

    }

    public WrongInfoAccessException(String message){
        super(message);
    }

    public WrongInfoAccessException(ExceptionCode errorCode){
        this.errorCode = errorCode;
    }

    public ExceptionCode getErrorCode(){return errorCode;}

}
