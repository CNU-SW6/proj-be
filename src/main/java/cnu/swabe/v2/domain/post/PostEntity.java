package cnu.swabe.v2.domain.post;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class PostEntity {
    private int no;
    private int userNo;
    private int imageNo;
    private String description;
    private int likeNum;
    private boolean isSell;
    private LocalDate createdAt;

    public PostEntity(int userNo, int imageNo, String description, int likeNum, boolean isSell, LocalDate createdAt) {
        this.userNo = userNo;
        this.imageNo = imageNo;
        this.description = description;
        this.likeNum = likeNum;
        this.isSell = isSell;
        this.createdAt = createdAt;
    }
}
