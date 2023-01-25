package cnu.swabe.v0.domain;

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
}
