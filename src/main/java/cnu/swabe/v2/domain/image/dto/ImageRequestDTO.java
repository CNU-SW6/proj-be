package cnu.swabe.v2.domain.image.dto;

import lombok.Data;

@Data
public class ImageRequestDTO {
    private int userNo;
    private String location;
    private String hatColor;
    private String topColor;
    private String pantsColor;
    private String shoesColor;
}
