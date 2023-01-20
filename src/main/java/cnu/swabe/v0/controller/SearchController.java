package cnu.swabe.v0.controller;

import cnu.swabe.v0.domain.Like;
import cnu.swabe.v0.domain.Post;
import cnu.swabe.v0.dto.LikeDTO;
import cnu.swabe.v0.dto.PostDTO;
import cnu.swabe.v0.dto.StyleDTO;
import cnu.swabe.v0.dto.presentation.postLikeRequestBodyDTO;
import cnu.swabe.v0.service.LikeService;
import cnu.swabe.v0.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class SearchController {
    private final PostService postService;
    private final LikeService likeService;

    @ResponseBody
    @GetMapping("/v0/search")
    public List<PostDTO> requestSearchStyle(@ModelAttribute StyleDTO styleDTO) {
        log.info("hat={}, top={}, pants={}, shoes={}", styleDTO.getHat(), styleDTO.getTop(), styleDTO.getPants(), styleDTO.getShoes());
        return postService.getPostItems(styleDTO);
    }

    @PatchMapping("/v0/likes/posts/{postNo}")
    public Like requestPostLike(@PathVariable int postNo, @RequestBody postLikeRequestBodyDTO postLikeRequestBodyDTO) {
        int userNo = postLikeRequestBodyDTO.getUserNo();
        LikeDTO likeDTO = new LikeDTO(postNo, userNo);
        Like like = likeService.addLike(likeDTO);
        return like;
    }
}
