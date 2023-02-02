package cnu.swabe.v2.service;

import cnu.swabe.v2.domain.post.PostEntity;
import cnu.swabe.v2.domain.post.dto.PostDTO;
import cnu.swabe.v2.domain.image.dto.ImageStyleRequestDTO;
import cnu.swabe.v2.domain.post.dto.PostSaveRequestDTO;
import cnu.swabe.v2.domain.post.dto.PostSaveResponseDTO;
import cnu.swabe.v2.domain.post.dto.PostSearchListResponseDTO;
import cnu.swabe.v2.repository.post.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;
    private final ImageService imageService;
    private final LikeService likeService;

    /**
     * version - v2
     * */
    public PostSaveResponseDTO savePost(PostSaveRequestDTO postSaveRequestDTO) {
        PostEntity postEntity = postRepository.save(postSaveRequestDTO);
        ModelMapper modelMapper = new ModelMapper();
        PostSaveResponseDTO postSaveResponse = modelMapper.map(postEntity, PostSaveResponseDTO.class);
        return postSaveResponse;
    }

    /**
     * version - v2
     * */
    public List<PostSearchListResponseDTO> getPosts(ImageStyleRequestDTO imageStyleRequestDTO) {
        List<PostEntity> posts = postRepository.findByImageStyle(imageStyleRequestDTO);
        List<PostSearchListResponseDTO> postSearchListResponse = new ArrayList<>();
        ModelMapper modelMapper = new ModelMapper();

        for(PostEntity postEntity : posts) {
            PostSearchListResponseDTO postSearchListDTO = modelMapper.map(postEntity, PostSearchListResponseDTO.class);
            postSearchListResponse.add(postSearchListDTO);
        }
        Collections.sort(postSearchListResponse);

        return postSearchListResponse;
    }

    /**
     * version - v1
     * 해당 유저가 맞는지 확인해야 할듯?
     * */
    public void deletePost(int postNo, int imageNo) {
        likeService.removeLikeRelationByPostNo(postNo);
        postRepository.deleteByPostNo(postNo);
        imageService.deleteImageInfo(imageNo);
    }

    public PostEntity getPostInfo (int postNo) {
        return postRepository.findByPostNo(postNo);
    }

    public List<PostDTO> getMyPosts(int userNo){
        return postRepository.findById(userNo);
    }
}

