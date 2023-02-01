package cnu.swabe.v1.service;

import cnu.swabe.v1.domain.like.Like;
import cnu.swabe.v1.domain.like.dto.LikeBusinessDTO;
import cnu.swabe.v1.repository.LikeRepository;
import cnu.swabe.v1.repository.post.PostDetailRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class LikeService {
    private final LikeRepository likeRepository;
    private final PostDetailRepository postDetailRepository;

    /**
     * 트랜잭션으로 처리되어야 할듯.. 하나만 처리되면 안됌
     * */
    public boolean clickLike(LikeBusinessDTO likeBusinessDTO) {
        boolean checked = likeBusinessDTO.isChecked();
        if(checked == false) { // 체크 X -> O
            LikeCount(likeBusinessDTO.getPostNo(), likeBusinessDTO.getLikeNum());
            addLikeRelation(likeBusinessDTO);
            return true;
        } else { // 체크 O -> X
            LikeDiscount(likeBusinessDTO.getPostNo(), likeBusinessDTO.getLikeNum());
            removeLikeRelation(likeBusinessDTO);
            return false;
        }
    }

    public int LikeCount(int postNo, int likeNum) {
        int addedLikeNum = postDetailRepository.plusLikeNumByPostNo(postNo, likeNum);
        return addedLikeNum;
    }

    public String addLikeRelation(LikeBusinessDTO likeBusinessDTO) {
        String pk = likeRepository.save(likeBusinessDTO);
        return pk;
    }

    public int LikeDiscount(int postNo, int likeNum) {
        int minusLikeNum = postDetailRepository.minusLikeNumByPostNo(postNo, likeNum);
        return minusLikeNum;
    }

    public void removeLikeRelation(LikeBusinessDTO likeBusinessDTO) {
        likeRepository.delete(likeBusinessDTO);
    }

    public List<LikeBusinessDTO> getLikePosts(int userNo) {
        List<LikeBusinessDTO> likePosts = likeRepository.findLikePost(userNo);
        return likePosts;
    }

    public void removeLikeRelationByPostNo(int postNo) {
        likeRepository.deleteByPostNo(postNo);
    }
}
