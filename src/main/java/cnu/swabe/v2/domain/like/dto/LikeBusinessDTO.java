package cnu.swabe.v2.domain.like.dto;

import lombok.Data;

@Data
public class LikeBusinessDTO {
    private int postNo;
    private int userNo;
    private int likeNum;
    private boolean checked;

    public LikeBusinessDTO(){

    }

    public LikeBusinessDTO(int postNo, int userNo, int likeNum) {
        this.postNo = postNo;
        this.userNo = userNo;
        this.likeNum = likeNum;
    }

    public LikeBusinessDTO(int postNo, int userNo, int likeNum, boolean checked) {
        this.postNo = postNo;
        this.userNo = userNo;
        this.likeNum = likeNum;
        this.checked = checked;
    }

}
