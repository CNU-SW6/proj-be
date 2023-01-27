package cnu.swabe.v0.controller;

import cnu.swabe.v0.dto.PostDTO;
import cnu.swabe.v0.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    // 마이페이지 정보 가져오기
    @GetMapping("/v0/posts/users/{userNo}")
    public List<PostDTO> requestMyPosts(@PathVariable int userNo) {
        // Posts_TB -> 내가 업로드한 이미지 select
        return postService.getMyPosts(userNo);
    }
}
