package cnu.swabe.v2.domain.post;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostEntity {
    private int postNo;
    private int userNo;
    private int imageNo;
    private String description;
    private int likeNum;
    private boolean isSell;
    @JsonProperty(value = "isMale")
    private boolean male;
    private String createdAt;

    public PostEntity(int userNo, int imageNo, String description, int likeNum, boolean isSell, boolean male, String createdAt) {
        this.userNo = userNo;
        this.imageNo = imageNo;
        this.description = description;
        this.likeNum = likeNum;
        this.isSell = isSell;
        this.male = male;
        this.createdAt = createdAt;
    }
}
