package cnu.swabe.v2.domain.post;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import static java.time.LocalDate.now;

@Getter @Setter
public class PostEntity {
    private int postNo;
    private int userNo;
    private int imageNo;
    private String description;
    private int likeNum;
    private boolean isSell;
    private String sellUrl;

    public PostEntity() {
    }

    private LocalDate createdAt;



    public PostEntity(int postNo, int userNo, int imageNo, String description, boolean isSell, int likeNum, String sellUrl) {
        this.postNo = postNo;
        this.userNo = userNo;
        this.imageNo = imageNo;
        this.description = description;
        this.isSell = isSell;
        this.likeNum = likeNum;
        this.sellUrl = sellUrl;
        this.createdAt = now();
    }
}
