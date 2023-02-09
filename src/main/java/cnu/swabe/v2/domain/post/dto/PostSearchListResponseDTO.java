package cnu.swabe.v2.domain.post.dto;

import lombok.Data;

@Data
public class PostSearchListResponseDTO implements Comparable<PostSearchListResponseDTO> {
    private int postNo;
    private boolean isSell;
    private String location;
    private int likeNum;

    @Override
    public int compareTo(PostSearchListResponseDTO o) {
        if(this.likeNum > o.getLikeNum()) {
            return -1;
        } else if(this.likeNum < o.getLikeNum()) {
            return 1;
        } else {
            return 0;
        }
    }
}
