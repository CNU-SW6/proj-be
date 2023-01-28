package cnu.swabe.v1.response;

import lombok.Getter;

@Getter
public class SuccessResponse {
    private boolean error;
    private Object data;

    public SuccessResponse() {
        this.error = false;
    }

    public SuccessResponse(Object object) {
        this.error = false;
        this.data = object;
    }
}
