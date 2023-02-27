package cnu.swabe.v2.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

// 상세정보 검색
@Data
public class PostAndUserDetailDTO {
    private String description;
    private int likeNum;
    @JsonProperty(value = "isSell")
    private boolean sell;
    private String nickname;
    private boolean isLike;
}
