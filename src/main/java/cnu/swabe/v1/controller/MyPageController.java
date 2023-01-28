package cnu.swabe.v1.controller;

import cnu.swabe.v1.domain.Post;
import cnu.swabe.v1.response.SuccessResponse;
import cnu.swabe.v1.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MyPageController {
    private final PostService postService;

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/v1/posts/{postNo}")
    public SuccessResponse requestDeletePost(@PathVariable int postNo) {
        postService.deletePost(postNo);
        return new SuccessResponse();
    }
}
