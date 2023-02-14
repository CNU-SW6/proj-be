package cnu.swabe.v2.domain.post.dto;

import lombok.Data;

// 삭제 로직
@Data
public class PostDeleteSideInfoRequestDTO {
    private int userNo;
    private int imageNo;
}
