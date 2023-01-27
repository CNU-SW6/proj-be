package cnu.swabe.v0.exception.custom;

public class NicknameDuplicatedException extends RuntimeException{
    public NicknameDuplicatedException() {
    }

    public NicknameDuplicatedException(Throwable cause) {
        super(cause);
    }
}
