package cnu.swabe.v1.controller;

import cnu.swabe.v1.domain.Image;
import cnu.swabe.v1.domain.Post;
import cnu.swabe.v1.service.ImageService;
import cnu.swabe.v1.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ShareController {
    private final ImageService imageService;
    private final PostService postService;

    @PostMapping("/v0/images")
    public int RequestImageInfo(@RequestBody Image image) {
        return imageService.saveImageInfo(image);
    }

    @PostMapping("/v0/posts")
    public int RequestPostInfo(@RequestBody Post post) {
        return postService.savePostInfo(post);
    }
}
