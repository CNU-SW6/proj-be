package cnu.swabe.v0.controller;

import cnu.swabe.v0.domain.like.Like;
import cnu.swabe.v0.domain.like.dto.LikeBusinessDTO;
import cnu.swabe.v0.dto.PostDTO;
import cnu.swabe.v0.dto.StyleDTO;
import cnu.swabe.v0.domain.like.dto.LikePresentationDTO;
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

    /**
     * postNo도 그냥 messageBody로 주면 안되는걸까?
     * */
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
}
