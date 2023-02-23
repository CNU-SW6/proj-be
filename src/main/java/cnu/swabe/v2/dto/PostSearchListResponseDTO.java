package cnu.swabe.v2.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostSearchListResponseDTO {
    private int postNo;
    private boolean isSell;
    private String location;
    private int likeNum;
}
