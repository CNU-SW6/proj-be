package cnu.swabe.v2.controller;

import cnu.swabe.v2.domain.image.dto.ImagePresentationDTO;
import cnu.swabe.v2.response.SuccessResponse;
import cnu.swabe.v2.service.LikeService;
import cnu.swabe.v2.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MyPageController {
    private final PostService postService;
    private final LikeService likeService;

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/api/posts/{postNo}")
    public SuccessResponse requestDeletePost(@RequestBody ImagePresentationDTO imagePresentationDTO, @PathVariable int postNo) {
        postService.deletePost(postNo, imagePresentationDTO.getImageNo());
        return new SuccessResponse();
    }

    // 마이페이지 내 게시물 가져오기
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/api/posts/users/{userNo}")
    public SuccessResponse requestMyPosts(@PathVariable int userNo) {
        // Posts_TB -> 내가 업로드한 이미지 select
        return new SuccessResponse(postService.getMyPosts(userNo));
    }

    // 마이페이지 좋아요 게시물 가져오기
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/api/posts/likes/users/{userNo}")
    public SuccessResponse requestLikePosts(@PathVariable int userNo) {
        return new SuccessResponse(likeService.getLikePosts(userNo));
    }
}
