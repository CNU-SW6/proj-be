package cnu.swabe.v2.domain.post.dto;

import lombok.Data;

@Data
public class PostSaveRequestDTO {
    private int userNo;
    private int imageNo;
    private String description;
    private boolean isSell;
    private String sellUrl;

}
