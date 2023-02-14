package cnu.swabe.v2.domain.post.dto.like;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LikeEntity {
    private String userLikePost;
    private int postNo;
    private int userNo;
    private int likeNum;

    public LikeEntity(String userLikePost, int postNo, int userNo, int likeNum) {
        this.userLikePost = userLikePost;
        this.postNo = postNo;
        this.userNo = userNo;
        this.likeNum = likeNum;
    }
}
