package cnu.swabe.v2.controller;

import cnu.swabe.v2.domain.post.dto.PostDeleteSideInfoRequestDTO;
import cnu.swabe.v2.domain.post.dto.PostSearchListResponseDTO;
import cnu.swabe.v2.response.SuccessResponse;
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
    @DeleteMapping("/v2.1/posts/{postNo}")
    public SuccessResponse requestDeletePost(
            @RequestBody PostDeleteSideInfoRequestDTO postDeleteSideInfoRequestDTO,
            @PathVariable int postNo
    ) {
        log.info("DeletePost::: postNo={}, userNo={}",
                postNo,
                postDeleteSideInfoRequestDTO.getUserNo()
        );

        postService.deletePost(postNo, postDeleteSideInfoRequestDTO);

        return new SuccessResponse();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/v2.1/posts/users/{userNo}")
    public SuccessResponse<List<PostSearchListResponseDTO>> requestMyPosts(@PathVariable int userNo) {
        log.info("MyPosts::: userNo={}", userNo);

        List<PostSearchListResponseDTO> postSearchListResponseDTO = postService.getMyPosts(userNo);

        return new SuccessResponse(postSearchListResponseDTO);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/v2.1/posts/likes/users/{userNo}")
    public SuccessResponse<List<PostSearchListResponseDTO>> requestLikePosts(@PathVariable int userNo) {
        log.info("LikePosts::: userNo={}", userNo);

        List<PostSearchListResponseDTO> postSearchListResponseDTO = postService.getLikePosts(userNo);

        return new SuccessResponse(postSearchListResponseDTO);
    }
}
