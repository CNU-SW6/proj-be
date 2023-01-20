package cnu.swabe.v0.dto;

import lombok.Data;

@Data
public class LikeDTO {
    private int postNo;
    private int userNo;

    public LikeDTO(int postNo, int userNo) {
        this.postNo = postNo;
        this.userNo = userNo;
    }
}
