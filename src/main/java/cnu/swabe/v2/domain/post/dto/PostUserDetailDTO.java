package cnu.swabe.v2.domain.post.dto;

import lombok.Data;

import java.time.LocalDate;

// 상세정보 검색
@Data
public class PostUserDetailDTO {
    private int postNo;
    private int userNo;
    private int imageNo;
    private String description;
    private int likeNum;
    private boolean isSell;
    private String sellUrl;
    private LocalDate createdAt;


    private String id;
    private String pw;
    private String nickname;
    private boolean isMale;
}
