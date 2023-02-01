package cnu.swabe.v2.domain.post.dto;

import lombok.Data;

@Data
public class PostSearchListDTO implements Comparable<PostSearchListDTO> {
    private int postNo;
    private boolean isSell;
    private String location;
    private int likeNum;

    @Override
    public int compareTo(PostSearchListDTO o) {
        if(this.likeNum > o.getLikeNum()) {
            return -1;
        } else if(this.likeNum < o.getLikeNum()) {
            return 1;
        } else {
            return 0;
        }
    }
}
