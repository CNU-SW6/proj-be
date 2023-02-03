package cnu.swabe.v2.controller;

import cnu.swabe.v2.domain.image.dto.ImagePresentationDTO;
import cnu.swabe.v2.domain.post.dto.PostDeleteSideInfoRequestDTO;
import cnu.swabe.v2.response.SuccessResponse;
import cnu.swabe.v2.domain.like.dto.LikeBusinessDTO;
import cnu.swabe.v2.domain.post.dto.PostDTO;
import cnu.swabe.v2.service.LikeService;
import cnu.swabe.v2.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MyPageController {
    private final PostService postService;
    private final LikeService likeService;

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/v2/posts/{postNo}")
    public SuccessResponse requestDeletePost(
            @RequestBody PostDeleteSideInfoRequestDTO postDeleteSideInfoRequestDTO,
            @PathVariable int postNo
    ) {
        log.info("DeletePost::: postNo={}, imageNo={}, userNo={}",
                postNo,
                postDeleteSideInfoRequestDTO.getImageNo(),
                postDeleteSideInfoRequestDTO.getUserNo()
        );
        postService.deletePost(postNo, postDeleteSideInfoRequestDTO);
        return new SuccessResponse();
    }

    // 마이페이지 내 게시물 가져오기
    @GetMapping("/v0/posts/users/{userNo}")
    public List<PostDTO> requestMyPosts(@PathVariable int userNo) {
        // Posts_TB -> 내가 업로드한 이미지 select
        return postService.getMyPosts(userNo);
    }

    // 마이페이지 좋아요 게시물 가져오기
    @GetMapping("/v0/posts/likes/users/{userNo}")
    public List<LikeBusinessDTO> requestLikePosts(@PathVariable int userNo) {
        return likeService.getLikePosts(userNo);
    }
}
