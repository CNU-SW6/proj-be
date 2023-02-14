package cnu.swabe.v2.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class UserSignUpDTO {

    @Getter
    @Builder
    @AllArgsConstructor
    public static class Request {
        private String id;
        private String pw;
        private String nickname;
        @JsonProperty(value = "isMale")
        private boolean male;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class Response {
        private int userNo;
    }
}
