package cnu.swabe.v1.controller;

import cnu.swabe.v1.domain.Post;
import cnu.swabe.v1.domain.like.Like;
import cnu.swabe.v1.domain.like.dto.LikeBusinessDTO;
import cnu.swabe.v1.dto.PostDTO;
import cnu.swabe.v1.dto.StyleDTO;
import cnu.swabe.v1.domain.like.dto.LikePresentationDTO;
import cnu.swabe.v1.response.SuccessResponse;
import cnu.swabe.v1.service.LikeService;
import cnu.swabe.v1.service.PostService;
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
    @GetMapping("/v1/posts")
    public SuccessResponse requestSearchStyle(@ModelAttribute StyleDTO styleDTO) {
        log.info("??? hat={}, top={}, pants={}, shoes={}", styleDTO.getHat(), styleDTO.getTop(), styleDTO.getPants(), styleDTO.getShoes());
        List<PostDTO> posts = postService.getPostItems(styleDTO);
        return new SuccessResponse(posts);
    }


    @PatchMapping("/v0/likes/posts/{postNo}")
    public Like requestPostLike(@PathVariable int postNo, @RequestBody LikePresentationDTO likePresentationDTO) {
        LikeBusinessDTO likeBusinessDTO = new LikeBusinessDTO(
                postNo,
                likePresentationDTO.getUserNo(),
                likePresentationDTO.getLikeNum()
        );
        Like like = likeService.clikeLike(likeBusinessDTO);
        return like;
    }

    // 게시물 선택
    @GetMapping("v0/posts/{postNo}")
    public Post selectPost(@PathVariable int postNo){
        return postService.getPostInfo(postNo);
    }
}
