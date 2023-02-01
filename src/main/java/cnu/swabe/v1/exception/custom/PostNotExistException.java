package cnu.swabe.v1.exception.custom;

import cnu.swabe.v1.exception.ExceptionCode;

public class PostNotExistException extends RuntimeException {
    ExceptionCode errorCode;

    public PostNotExistException(){
    }
    public PostNotExistException(String message){
        super(message);
    }

    public PostNotExistException(ExceptionCode errorCode){this.errorCode = errorCode;}

    public ExceptionCode getErrorCode(){return errorCode;}
}
