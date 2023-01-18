package cnu.swabe.v0.controller;

import cnu.swabe.v0.domain.Post;
import cnu.swabe.v0.dto.PostDTO;
import cnu.swabe.v0.dto.StyleDTO;
import cnu.swabe.v0.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class SearchController {
    private final PostService postService;

    @ResponseBody
    @GetMapping("/search")
    public List<PostDTO> requestSearchStyle(@ModelAttribute StyleDTO styleDTO) {
        log.info("hat={}, top={}, pants={}, shoes={}", styleDTO.getHat(), styleDTO.getTop(), styleDTO.getPants(), styleDTO.getShoes());
        return postService.getPostItems(styleDTO);
    }
}
