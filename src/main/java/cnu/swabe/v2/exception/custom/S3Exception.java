package cnu.swabe.v2.exception.custom;

import cnu.swabe.v2.exception.ExceptionCode;

public class S3Exception extends RuntimeException {
    ExceptionCode errorCode;

    public S3Exception(){
    }
    public S3Exception(String message){
        super(message);
    }

    public S3Exception(ExceptionCode errorCode){this.errorCode = errorCode;}

    public ExceptionCode getErrorCode(){return errorCode;}
}