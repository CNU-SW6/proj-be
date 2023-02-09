package cnu.swabe.v2.domain;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Post {
    private int postNo;
    private int userNo;
    private int imageNo;
    private String description;
    private boolean isSell;
    private int likeNum;
    private String sellUrl;

    public Post() {
    }

    public Post(int postNo, int userNo, int imageNo, String description, boolean isSell, int likeNum, String sellUrl) {
        this.postNo = postNo;
        this.userNo = userNo;
        this.imageNo = imageNo;
        this.description = description;
        this.isSell = isSell;
        this.likeNum = likeNum;
        this.sellUrl = sellUrl;
    }
}
