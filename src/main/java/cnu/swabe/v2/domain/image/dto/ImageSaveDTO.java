package cnu.swabe.v2.domain.image.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

public class ImageSaveDTO {

    @Getter
    @Builder
    @AllArgsConstructor
    public static class Request {
        private int userNo;
        private String Location;
        private String hatColor;
        private String topColor;
        private String pantsColor;
        private String shoesColor;

        public void setLocation(String location) {
            Location = location;
        }
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class Response {
        private int imageNo;
    }
}
