package cnu.swabe.v1.exception.custom;

public class NicknameDuplicatedException extends RuntimeException{
    public NicknameDuplicatedException() {
    }

    public NicknameDuplicatedException(Throwable cause) {
        super(cause);
    }
}
