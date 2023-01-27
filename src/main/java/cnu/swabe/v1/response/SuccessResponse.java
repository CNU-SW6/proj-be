package cnu.swabe.v1.response;

import lombok.Getter;

@Getter
public class SuccessResponse {
    private boolean error;

    public SuccessResponse() {
        this.error = false;
    }
}
