package cnu.swabe.v0.domain.like.dto;

import lombok.Data;

@Data
public class LikeBusinessDTO {
    private int postNo;
    private int userNo;
    private int likeNum;

    public LikeBusinessDTO(int postNo, int userNo, int likeNum) {
        this.postNo = postNo;
        this.userNo = userNo;
        this.likeNum = likeNum;
    }
}
