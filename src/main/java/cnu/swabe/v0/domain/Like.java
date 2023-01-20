package cnu.swabe.v0.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Like {
    private String userLikePost;
    private int postNo;
    private int userNo;

    public Like(String userLikePost, int postNo, int userNo) {
        this.userLikePost = userLikePost;
        this.postNo = postNo;
        this.userNo = userNo;
    }
}
