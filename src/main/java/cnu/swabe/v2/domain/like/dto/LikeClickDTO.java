package cnu.swabe.v2.domain.like.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


public class LikeClickDTO {
    @Getter
    @Builder
    @AllArgsConstructor
    public static class Request {
        private int userNo;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class Response {
        private int likeNum;
    }
}
