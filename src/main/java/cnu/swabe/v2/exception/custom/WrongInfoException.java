package cnu.swabe.v2.exception.custom;

import cnu.swabe.v2.exception.ExceptionCode;

public class WrongInfoException extends RuntimeException {
    ExceptionCode errorCode;
    public WrongInfoException(){

    }

    public WrongInfoException(String message){
        super(message);
    }

    public WrongInfoException(ExceptionCode errorCode){
        this.errorCode = errorCode;
    }

    public ExceptionCode getErrorCode(){return errorCode;}

}
