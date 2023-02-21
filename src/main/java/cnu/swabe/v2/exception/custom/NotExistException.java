package cnu.swabe.v2.exception.custom;

import cnu.swabe.v2.exception.ExceptionCode;

public class NotExistException extends RuntimeException {
    ExceptionCode errorCode;

    public NotExistException(){
    }
    public NotExistException(String message){
        super(message);
    }

    public NotExistException(ExceptionCode errorCode){this.errorCode = errorCode;}

    public ExceptionCode getErrorCode(){return errorCode;}
}
