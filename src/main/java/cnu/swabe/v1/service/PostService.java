package cnu.swabe.v1.service;

import cnu.swabe.v1.domain.Post;
import cnu.swabe.v1.dto.ImageInfoDTO;
import cnu.swabe.v1.dto.PostDTO;
import cnu.swabe.v1.dto.StyleDTO;
import cnu.swabe.v1.repository.post.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;
    private final ImageService imageService;

    /**
     *
     * */
    public Post savePostInfo(Post postDTO) {
        Post post = postRepository.save(postDTO);
        return post;
    }

    /**
     * version - v1
     * 예외를 만들어 던질 필요가 있을까?
     * */
    public List<PostDTO> getPostItems(StyleDTO styleDTO) {
        List<ImageInfoDTO> imageInfoDTO = imageService.getImageInfo(styleDTO);
        if(imageInfoDTO == null) {
            return null;
        }

        List<PostDTO> posts = postRepository.findByImageInfo(imageInfoDTO);
        return posts;
    }

    public void deletePost(int postNo) {
        postRepository.deleteByPostNo(postNo);
        return;
    }

    public Post getPostInfo (int postNo) {
        return postRepository.findByPostNo(postNo);
    }
}

