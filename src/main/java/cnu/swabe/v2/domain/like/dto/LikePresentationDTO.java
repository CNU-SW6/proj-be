package cnu.swabe.v2.domain.like.dto;

import lombok.Data;

@Data
public class LikePresentationDTO {
    private int userNo;
    private int likeNum;
    private boolean checked;
}
