package cnu.swabe.v0.service;

import cnu.swabe.v0.dto.ImageInfoDTO;
import cnu.swabe.v0.dto.PostDTO;
import cnu.swabe.v0.dto.StyleDTO;
import cnu.swabe.v0.repository.post.PostRepository;
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

    public List<PostDTO> getPostItems(StyleDTO styleDTO) {
        List<ImageInfoDTO> imageInfoDTO = imageService.getImageInfo(styleDTO);
        return postRepository.findByImageInfo(imageInfoDTO);
    }

    public void deletePost(int postNo) {
        postRepository.deleteByPostNo(postNo);
        return;
    }
}
