package cnu.swabe.v2.service;


import cnu.swabe.v2.domain.image.dto.ImageSaveDTO;
import cnu.swabe.v2.domain.post.PostEntity;
import cnu.swabe.v2.domain.post.dto.*;
import cnu.swabe.v2.domain.image.dto.ImageStyleRequestDTO;
import cnu.swabe.v2.dto.PostDTO;
import cnu.swabe.v2.exception.ExceptionCode;
import cnu.swabe.v2.exception.custom.CannotBeDeletedException;
import cnu.swabe.v2.exception.custom.PostNotExistException;
import cnu.swabe.v2.exception.custom.WrongPostFormException;
import cnu.swabe.v2.repository.post.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final ModelMapper modelMapper = new ModelMapper();

    /**
     * version - v2.1
     * DTO에 비즈니스로직을 넣고 메소드를 호출하면 더 나을것으로 보이는데 , , ,
     * */
    @Transactional
    public PostSaveDTO.Response savePost(PostSaveDTO.Request postSaveRequestDTO) {
        if(postSaveRequestDTO.isSell()) {
            if(postSaveRequestDTO.getSellUrl() == null || postSaveRequestDTO.getSellUrl().equals("")) {
                throw new WrongPostFormException(ExceptionCode.NO_EXIST_POST_URL);
            }
        }

        if(postSaveRequestDTO.getHatColor() == null && postSaveRequestDTO.getTopColor() == null &&
                postSaveRequestDTO.getPantsColor() == null && postSaveRequestDTO.getShoesColor() == null
        ) {
            throw new WrongPostFormException(ExceptionCode.NO_EXIST_COLOR);
        }

        ImageSaveDTO.Request imageSaveRequestDTO = modelMapper.map(postSaveRequestDTO, ImageSaveDTO.Request.class);
        ImageSaveDTO.Response imageSaveResponseDTO = imageService.saveImage(imageSaveRequestDTO);
        PostEntity post = modelMapper.map(postSaveRequestDTO, PostEntity.class);
        post = modelMapper.map(imageSaveResponseDTO, PostEntity.class);

        PostSaveDTO.Response postSaveResponseDTO = modelMapper.map(post, PostSaveDTO.Response.class);

        return postSaveResponseDTO;
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
     * version - v2
     * logic. 이미지삭제 -> 좋아요삭제 -> 게시물삭제
     * 게시물 삭제할 때
     * */
    @Transactional
    public void deletePost(int postNo, PostDeleteSideInfoRequestDTO postDeleteSideInfoRequestDTO) {
        PostUserDetailDTO post = postRepository.findByPostNo(postNo);
        if(post.getUserNo() != postDeleteSideInfoRequestDTO.getUserNo()) {
            throw new CannotBeDeletedException(ExceptionCode.DIFFERENCE_USER_NO_AND_POST_USER_NO);
        }
        postRepository.deleteByPostNo(postNo);
        imageService.deleteImage(postDeleteSideInfoRequestDTO.getImageNo());
        likeService.removeLikeRelationByPostNo(postNo);
    }

    public PostUserDetailDTO getPostInfo (int postNo) {
        PostUserDetailDTO byPostNo = postRepository.findByPostNo(postNo);
        if(byPostNo == null) throw new PostNotExistException(ExceptionCode.NO_EXIST_POST);

        return byPostNo;
    }

    public List<PostDTO> getMyPosts(int userNo){
        return postRepository.findById(userNo);
    }
}

