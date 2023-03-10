package cnu.swabe.v2.service;

import cnu.swabe.v2.domain.like.LikeEntity;
import cnu.swabe.v2.domain.like.dto.LikeClickDTO;
import cnu.swabe.v2.domain.post.PostEntity;
import cnu.swabe.v2.repository.LikeRepository;
import cnu.swabe.v2.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class LikeService {
    private final LikeRepository likeRepository;
    private final PostRepository postRepository;

    /**
     * version - v2.1
     * */
    @Transactional
    public LikeClickDTO.Response clickLike(int postNo, LikeClickDTO.Request likeBusinessRequestDTO) {
        boolean alreadyLiked = inquireLikeRelation(postNo, likeBusinessRequestDTO.getUserNo());
        LikeEntity like = new LikeEntity(postNo, likeBusinessRequestDTO.getUserNo());
        PostEntity post = postRepository.findByPostNo(postNo);
        int likeNum = post.getLikeNum();
        if (alreadyLiked) {
            likeNum = likeNum -1;
            likeRepository.delete(like);
            postRepository.updateLikeNum(postNo, likeNum);
        } else {
            likeNum = likeNum +1;
            likeRepository.save(like);
            postRepository.updateLikeNum(postNo, likeNum);
        }

        LikeClickDTO.Response likeClickResponseDTO = new LikeClickDTO.Response(likeNum);
        return likeClickResponseDTO;
    }

    /**
     * version - v2.1
     * */
    public boolean inquireLikeRelation(int postNo, int userNo) {
        LikeEntity like = likeRepository.findByPostNoAndUserNo(postNo, userNo);
        if(like == null) {
            return false;
        }

        return true;
    }

    /**
     * version - v2.1
     * */
    public void removeLikeRelationByPostNo(int postNo) {
        likeRepository.deleteByPostNo(postNo);
    }

    /**
     * version - v2.1
     * */
    public List<Integer> findPostNosByUserNo(int userNo) {
        List<LikeEntity> likes = likeRepository.findByUserNo(userNo);
        List<Integer> postNos = new ArrayList<>();

        for(LikeEntity like : likes) {
            postNos.add(like.getPostNo());
        }

        return postNos;
    }
}
