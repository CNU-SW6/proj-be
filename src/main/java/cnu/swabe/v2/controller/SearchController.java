package cnu.swabe.v2.controller;

import cnu.swabe.v2.domain.like.dto.LikeClickDTO;
import cnu.swabe.v2.dto.PostAndUserDetailDTO;
import cnu.swabe.v2.dto.StyleSearchRequestDTO;
import cnu.swabe.v2.dto.PostSearchListResponseDTO;
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
    @GetMapping("/api/posts")
    public SuccessResponse<List<PostSearchListResponseDTO>> requestSearchStyle(@ModelAttribute StyleSearchRequestDTO styleSearchRequestDTO) {
        log.info("SearchStyle::: hatColor={}, topColor={}, pantsColor={}, shoesColor={}, gender={}, sort={}",
                styleSearchRequestDTO.getHatColor(),
                styleSearchRequestDTO.getTopColor(),
                styleSearchRequestDTO.getPantsColor(),
                styleSearchRequestDTO.getShoesColor(),
                styleSearchRequestDTO.getGender(),
                styleSearchRequestDTO.getSort()
        );

        List<PostSearchListResponseDTO> postSearchListResponse = postService.getPosts(styleSearchRequestDTO);

        return new SuccessResponse(postSearchListResponse);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/api/posts/{postNo}/{userNo}")
    public SuccessResponse<PostAndUserDetailDTO> requestPostDetail(@PathVariable(value = "postNo") int postNo, @PathVariable(value = "userNo") int userNo) {
        log.info("PostDetail::: postNo={}, userNo={}", postNo, userNo);

        PostAndUserDetailDTO postAndUserDetailDTO = postService.getPostDetail(postNo);

        postAndUserDetailDTO.setLike(likeService.inquireLikeRelation(postNo, userNo));

        return new SuccessResponse(postAndUserDetailDTO);
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/api/likes/posts/{postNo}")
    public SuccessResponse<LikeClickDTO.Response> requestLike(@PathVariable int postNo, @RequestBody LikeClickDTO.Request likeClickRequestDTO) {
        log.info("PostDetail::: postNo={}, userNo={}",
                postNo,
                likeClickRequestDTO.getUserNo()
        );

        LikeClickDTO.Response likeClickResponseDTO = likeService.clickLike(postNo, likeClickRequestDTO);

        return new SuccessResponse(likeClickResponseDTO);
    }
}
