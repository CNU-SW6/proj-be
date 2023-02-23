package cnu.swabe.v2.domain.post.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

public class PostSaveDTO {

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Request {
        private int userNo;
        private String location;
        private String hatColor;
        private String topColor;
        private String pantsColor;
        private String shoesColor;
        @JsonProperty(value = "isMale")
        private boolean male;
        private String description;
        @JsonProperty(value = "isSell")
        private boolean sell;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Response {
        private int postNo;
        private int likeNum;
    }
}
