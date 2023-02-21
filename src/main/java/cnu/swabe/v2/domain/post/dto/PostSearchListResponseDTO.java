package cnu.swabe.v2.domain.post.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class PostSearchListResponseDTO {
    private int postNo;
    private boolean isSell;
    private String location;
    private int likeNum;
}
