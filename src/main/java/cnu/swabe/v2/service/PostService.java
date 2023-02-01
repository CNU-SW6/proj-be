package cnu.swabe.v2.service;

import cnu.swabe.v2.domain.post.PostEntity;
import cnu.swabe.v2.domain.post.dto.PostDTO;
import cnu.swabe.v2.domain.post.dto.PostSearchListDTO;
import cnu.swabe.v2.domain.image.dto.ImageStyleDTO;
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
     * version - v1
     * */
    public PostEntity savePostInfo(PostEntity postEntityDTO) {
        PostEntity postEntity = postRepository.save(postEntityDTO);
        return postEntity;
    }

    /**
     * version - v2
     * */
    public List<PostSearchListDTO> getPosts(ImageStyleDTO imageStyleDTO) {
        List<PostEntity> posts = postRepository.findByImageStyle(imageStyleDTO);
        List<PostSearchListDTO> postSearchList = new ArrayList<>();
        ModelMapper modelMapper = new ModelMapper();

        for(PostEntity postEntity : posts) {
            PostSearchListDTO postSearchListDTO = modelMapper.map(postEntity, PostSearchListDTO.class);
            postSearchList.add(postSearchListDTO);
        }
        Collections.sort(postSearchList);

        return postSearchList;
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

