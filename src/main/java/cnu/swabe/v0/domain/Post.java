package cnu.swabe.v0.domain;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Post {
    private int postNo;
    private int userNo;
    private int imageNo;
    private String description;
    private int likeNum;
    private boolean isSell;
    private String sellUrl;
}
