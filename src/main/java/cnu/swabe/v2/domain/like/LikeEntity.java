package cnu.swabe.v2.domain.like;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LikeEntity {
    private String userNoLikePostNo;
    private int postNo;
    private int userNo;


    public LikeEntity(int postNo, int userNo) {
        this.postNo = postNo;
        this.userNo = userNo;
    }
}
