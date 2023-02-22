package cnu.swabe.v2.service;


import cnu.swabe.v2.domain.image.ImageEntity;
import cnu.swabe.v2.domain.image.dto.ImageSaveDTO;
import cnu.swabe.v2.domain.post.PostEntity;
import cnu.swabe.v2.domain.post.dto.*;
import cnu.swabe.v2.dto.StyleRequestDTO;
import cnu.swabe.v2.exception.ExceptionCode;
import cnu.swabe.v2.exception.custom.CannotBeDeletedException;
import cnu.swabe.v2.exception.custom.NotExistException;
import cnu.swabe.v2.exception.custom.S3Exception;
import cnu.swabe.v2.exception.custom.WrongPostFormException;
import cnu.swabe.v2.repository.ImageRepository;
import cnu.swabe.v2.repository.PostRepository;
import cnu.swabe.v2.s3.S3Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;
    private final ImageService imageService;
    private final LikeService likeService;
    private final S3Service s3Service;
    private final ModelMapper modelMapper = new ModelMapper();

    /**
     * version - v2.1
     * DTO에 비즈니스로직을 넣고 메소드를 호출하면 더 나을것으로 보이는데 , , ,
     * */
    @Transactional
    public PostSaveDTO.Response savePost(PostSaveDTO.Request postSaveRequestDTO) {
        if(postSaveRequestDTO.isSell()) {
            if(postSaveRequestDTO.getDescription() == null || postSaveRequestDTO.getDescription().equals("")) {
                throw new WrongPostFormException(ExceptionCode.NO_EXIST_POST_URL);
            }
        }

        if(postSaveRequestDTO.getHatColor() == null && postSaveRequestDTO.getTopColor() == null &&
                postSaveRequestDTO.getPantsColor() == null && postSaveRequestDTO.getShoesColor() == null
        ) {
            throw new WrongPostFormException(ExceptionCode.NO_EXIST_COLOR);
        }

        String imageS3Url;
        try {
            imageS3Url = s3Service.upload(postSaveRequestDTO.getImageFile());
        } catch(Exception e) {
            throw new S3Exception(ExceptionCode.CANNOT_UPLOAD_S3);
        }

        ImageSaveDTO.Request imageSaveRequestDTO = modelMapper.map(postSaveRequestDTO, ImageSaveDTO.Request.class);
        imageSaveRequestDTO.setLocation(imageS3Url);
        ImageSaveDTO.Response imageSaveResponseDTO = imageService.saveImage(imageSaveRequestDTO);
        PostEntity post = modelMapper.map(postSaveRequestDTO, PostEntity.class);
        post.setImageNo(imageSaveResponseDTO.getImageNo());
        postRepository.save(post);
        PostSaveDTO.Response postSaveResponseDTO = modelMapper.map(post, PostSaveDTO.Response.class);

        return postSaveResponseDTO;
    }

    /**
     * version - v2.1
     * */
    public List<PostSearchListResponseDTO> getPosts(StyleRequestDTO styleRequestDTO) {
        List<PostEntity> posts = postRepository.findByImageStyle(styleRequestDTO);
        List<PostSearchListResponseDTO> postSearchListResponse = new ArrayList<>();

        for(PostEntity postEntity : posts) {
            PostSearchListResponseDTO postSearchListDTO = modelMapper.map(postEntity, PostSearchListResponseDTO.class);
            postSearchListResponse.add(postSearchListDTO);
        }

        return postSearchListResponse;
    }

    /**
     * version - v2.1
     * */
    @Transactional
    public void deletePost(int postNo, PostDeleteSideInfoRequestDTO postDeleteSideInfoRequestDTO) {
        PostEntity post = postRepository.findByPostNo(postNo);
        if(post.getUserNo() != postDeleteSideInfoRequestDTO.getUserNo()) {
            throw new CannotBeDeletedException(ExceptionCode.DIFFERENCE_USER_NO_AND_POST_USER_NO);
        }

        ImageEntity image = imageService.findImageByImageNo(post.getImageNo());
        s3Service.delete(image.getFileName()); // S3 삭제
        likeService.removeLikeRelationByPostNo(postNo); // 좋아요 정보 삭제
        postRepository.deleteByPostNo(postNo); // 게시글 삭제
        imageService.deleteImage(image.getImageNo()); // 이미지 정보 삭제
    }

    /**
     * version - v2.1
     * */
    public PostUserDetailDTO getPostDetail(int postNo) {
        PostUserDetailDTO postUserDetailDTO = postRepository.findPostAndUserByPostNo(postNo);
        if(postUserDetailDTO == null) {
            throw new NotExistException(ExceptionCode.NO_EXIST_POST);
        }

        return postUserDetailDTO;
    }

    /**
     * version - v2.1
     * */
    public List<PostSearchListResponseDTO> getMyPosts(int userNo) {
        List<PostEntity> posts = postRepository.findByUserNo(userNo);
        List<PostSearchListResponseDTO> postSearchListResponseDTO = new ArrayList<>();

        for(PostEntity post : posts) {
            postSearchListResponseDTO.add(modelMapper.map(post, PostSearchListResponseDTO.class));
        }

        return postSearchListResponseDTO;
    }

    /**
     * version - v2.1
     * */
    public List<PostSearchListResponseDTO> getLikePosts(int userNo) {
        List<Integer> postNos = likeService.findPostNoByUserNo(userNo);


        return likePosts;
    }
}

