package cnu.swabe.v2.domain.post.dto;

import lombok.Data;

@Data
public class PostUserDetailDTO {
    private int postNo;
    private int userNo;
    private int imageNo;
    private String description;
    private boolean isSell;
    private int likeNum;
    private String sellUrl;


    private String id;
    private String pw;
    private String nickname;
    private boolean isMale;
}
