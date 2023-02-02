package cnu.swabe.v2.controller;

import cnu.swabe.v2.domain.post.PostEntity;
import cnu.swabe.v2.domain.like.dto.LikeBusinessDTO;
import cnu.swabe.v2.domain.image.dto.ImageStyleRequestDTO;
import cnu.swabe.v2.domain.like.dto.LikePresentationDTO;
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
    @GetMapping("/v2/posts")
    public SuccessResponse<List<PostSearchListResponseDTO>> requestSearchStyle(@ModelAttribute ImageStyleRequestDTO imageStyleRequestDTO) {
        log.info("SearchStyle::: hatColor={}, topColor={}, pantsColor={}, shoesColor={}",
                imageStyleRequestDTO.getHat(),
                imageStyleRequestDTO.getTop(),
                imageStyleRequestDTO.getPants(),
                imageStyleRequestDTO.getShoes()
        );
        List<PostSearchListResponseDTO> postSearchListResponse = postService.getPosts(imageStyleRequestDTO);
        return new SuccessResponse(postSearchListResponse);
    }

    // 게시물 선택
    @GetMapping("v0/posts/{postNo}")
    public PostEntity selectPost(@PathVariable int postNo){
        return postService.getPostInfo(postNo);
    }


    // 게시물 좋아요
    @PatchMapping("/v0/likes/posts/{postNo}")
    public boolean requestLike(@PathVariable int postNo, @RequestBody LikePresentationDTO likePresentationDTO) {
        LikeBusinessDTO likeBusinessDTO = new LikeBusinessDTO(
                postNo,
                likePresentationDTO.getUserNo(),
                likePresentationDTO.getLikeNum(),
                likePresentationDTO.isChecked()
        );
        return likeService.clickLike(likeBusinessDTO);
    }
}
