package cnu.swabe.v2.domain.post;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import static java.time.LocalDate.now;

@Getter @Setter
public class PostEntity {
    private int no;
    private int userNo;
    private int imageNo;
    private String description;
    private int likeNum;
    private boolean isSell;
    private String sellUrl;
    private LocalDate createdAt;

    public PostEntity() {
    }

    public PostEntity(int userNo, int imageNo, String description, boolean isSell, int likeNum, String sellUrl) {
        this.userNo = userNo;
        this.imageNo = imageNo;
        this.description = description;
        this.isSell = isSell;
        this.likeNum = likeNum;
        this.sellUrl = sellUrl;
        this.createdAt = now();
    }
}
