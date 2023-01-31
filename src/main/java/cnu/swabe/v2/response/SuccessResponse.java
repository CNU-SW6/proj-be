package cnu.swabe.v2.response;

import lombok.Getter;

@Getter
public class SuccessResponse<T> {
    private boolean error;
    private T data;

    public SuccessResponse() {
        this.error = false;
    }

    public SuccessResponse(T data) {
        this.error = false;
        this.data = data;
    }
}
