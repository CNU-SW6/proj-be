package cnu.swabe.v0.controller;

import cnu.swabe.v0.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MyPageController {
    private final PostService postService;

    @DeleteMapping("/v0/posts/{postNo}")
    public String requestDeletePost(@PathVariable int postNo) {
        postService.deletePost(postNo);
        return "ok";
    }
}
