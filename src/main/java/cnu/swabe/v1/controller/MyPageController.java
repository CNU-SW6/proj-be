package cnu.swabe.v1.controller;

import cnu.swabe.v1.domain.Post;
import cnu.swabe.v1.response.SuccessResponse;
import cnu.swabe.v1.domain.like.dto.LikeBusinessDTO;
import cnu.swabe.v1.dto.PostDTO;
import cnu.swabe.v1.service.LikeService;
import cnu.swabe.v1.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MyPageController {
    private final PostService postService;
    private final LikeService likeService;

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/v1/posts/{postNo}")
    public SuccessResponse requestDeletePost(@PathVariable int postNo) {
        postService.deletePost(postNo);
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
