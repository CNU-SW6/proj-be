package cnu.swabe.v2.exception.custom;

public class IdDuplicatedException extends RuntimeException{
    public IdDuplicatedException(){}
    public IdDuplicatedException(Throwable cause) {
        super(cause);
    }
}
