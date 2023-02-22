package cnu.swabe.v2.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class UserLoginDTO {

    @Getter
    @Builder
    @AllArgsConstructor
    public static class Request {
        private String id;
        private String pw;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class Response {
        private int userNo;
    }
}
