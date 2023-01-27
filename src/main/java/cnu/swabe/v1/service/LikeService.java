package cnu.swabe.v1.service;

import cnu.swabe.v1.domain.like.Like;
import cnu.swabe.v1.domain.like.dto.LikeBusinessDTO;
import cnu.swabe.v1.repository.LikeRepository;
import cnu.swabe.v1.repository.post.PostDetailRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class LikeService {
    private final LikeRepository likeRepository;
    private final PostDetailRepository postDetailRepository;

    /**
     * 트랜잭션으로 처리되어야 할듯.. 하나만 처리되면 안됌
     * */
    public Like clikeLike(LikeBusinessDTO likeBusinessDTO) {
        int addedLikeNum = addLikeCount(likeBusinessDTO.getPostNo(), likeBusinessDTO.getLikeNum());
        String pk = addLikeRelation(likeBusinessDTO);
        Like like = new Like(pk, likeBusinessDTO.getPostNo(), likeBusinessDTO.getUserNo(), addedLikeNum);
        return like;
    }

    public int addLikeCount(int postNo, int likeNum) {
        int addedLikeNum = postDetailRepository.updateLikeNumByPostNo(postNo, likeNum);
        return addedLikeNum;
    }

    public String addLikeRelation(LikeBusinessDTO likeBusinessDTO) {
        String pk = likeRepository.save(likeBusinessDTO);
        return pk;
    }
}
