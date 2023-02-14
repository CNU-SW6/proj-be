package cnu.swabe.v2.domain.post.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

public class PostSaveDTO {

    @Data
    @Builder
    public static class RequestDTO{
        private int userNo;
        private int imageNo;
        private String description;
        @JsonProperty(value = "isSell")
        private boolean isSell;
        private String sellUrl;
    }

    @Data
    @Builder
    public static class ResponseDTO{
        private int postNo;
        private int likeNum;
    }
}
