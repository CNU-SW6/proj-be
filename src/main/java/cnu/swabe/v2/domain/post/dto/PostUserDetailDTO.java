package cnu.swabe.v2.domain.post.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;

// 상세정보 검색
@Data
public class PostUserDetailDTO {
    private String description;
    private int likeNum;
    @JsonProperty(value = "isSell")
    private boolean sell;
    private String nickname;
}
