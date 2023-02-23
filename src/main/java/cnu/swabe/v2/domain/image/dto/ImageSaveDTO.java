package cnu.swabe.v2.domain.image.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

public class ImageSaveDTO {

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
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Response {
        private int imageNo;
    }
}
