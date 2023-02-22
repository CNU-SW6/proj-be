package cnu.swabe.v2.domain.post.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class PostDeleteSideInfoRequestDTO {
    private int userNo;
}
