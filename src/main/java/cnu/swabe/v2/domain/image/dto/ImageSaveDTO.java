package cnu.swabe.v2.domain.image.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

public class ImageSaveDTO {

    @Getter
    @Builder
    @AllArgsConstructor
    public static class Request {
        private int userNo;
        private MultipartFile imageFile;
        private String hatColor;
        private String topColor;
        private String pantsColor;
        private String shoesColor;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class Response {
        private int imageNo;
    }
}
