package cnu.swabe.v2.domain.post.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PostSaveRequestDTO {
    private int userNo;
    private int imageNo;
    private String description;
    @JsonProperty(value = "isSell")
    private boolean isSell;
    private String sellUrl;

}
