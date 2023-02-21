package cnu.swabe.v2.controller;

import cnu.swabe.v2.domain.like.dto.LikeClickDTO;
import cnu.swabe.v2.domain.post.dto.PostUserDetailDTO;
import cnu.swabe.v2.dto.StyleRequestDTO;
import cnu.swabe.v2.domain.post.dto.PostSearchListResponseDTO;
import cnu.swabe.v2.response.SuccessResponse;
import cnu.swabe.v2.service.LikeService;
import cnu.swabe.v2.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class SearchController {
    private final PostService postService;
    private final LikeService likeService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/v2.1/posts")
    public SuccessResponse<List<PostSearchListResponseDTO>> requestSearchStyle(@ModelAttribute StyleRequestDTO styleRequestDTO) {
        log.info("SearchStyle::: hatColor={}, topColor={}, pantsColor={}, shoesColor={}, isMale={}, isOrderByLikeNum={}",
                styleRequestDTO.getHatColor(),
                styleRequestDTO.getTopColor(),
                styleRequestDTO.getPantsColor(),
                styleRequestDTO.getShoesColor(),
                styleRequestDTO.isMale(),
                styleRequestDTO.isOrderByLikeNum()

        );

        List<PostSearchListResponseDTO> postSearchListResponse = postService.getPosts(styleRequestDTO);

        return new SuccessResponse(postSearchListResponse);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/v2.1/posts/{postNo}")
    public SuccessResponse<PostUserDetailDTO> requestPostDetail(@PathVariable int postNo) {
        log.info("PostDetail::: postNo={}", postNo);

        PostUserDetailDTO postUserDetailDTO = postService.getPostDetail(postNo);

        return new SuccessResponse(postUserDetailDTO);
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/v2.1/likes/posts/{postNo}")
    public SuccessResponse<LikeClickDTO.Response> requestLike(@PathVariable int postNo, @RequestBody LikeClickDTO.Request likeClickRequestDTO) {
        log.info("PostDetail::: postNo={}, userNo={}",
                postNo,
                likeClickRequestDTO.getUserNo()
        );

        LikeClickDTO.Response likeClickResponseDTO = likeService.clickLike(postNo, likeClickRequestDTO);

        return new SuccessResponse(likeClickResponseDTO);
    }
}
