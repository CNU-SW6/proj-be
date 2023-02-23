package cnu.swabe.v2.service;


import cnu.swabe.v2.domain.image.ImageEntity;
import cnu.swabe.v2.domain.image.dto.ImageSaveDTO;
import cnu.swabe.v2.domain.post.PostEntity;
import cnu.swabe.v2.domain.post.dto.*;
import cnu.swabe.v2.dto.PostSearchListResponseDTO;
import cnu.swabe.v2.dto.PostAndUserDetailDTO;
import cnu.swabe.v2.dto.StyleSearchRequestDTO;
import cnu.swabe.v2.exception.ExceptionCode;
import cnu.swabe.v2.exception.custom.CannotBeDeletedException;
import cnu.swabe.v2.exception.custom.NotExistException;
import cnu.swabe.v2.exception.custom.WrongPostFormException;
import cnu.swabe.v2.repository.PostRepository;
import cnu.swabe.v2.s3.S3Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
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
    private final ModelMapper modelMapper = new ModelMapper() {{
        getConfiguration()
            .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE)
            .setFieldMatchingEnabled(true);
    }};

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

        ImageSaveDTO.Request imageSaveRequestDTO = modelMapper.map(postSaveRequestDTO, ImageSaveDTO.Request.class);
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
    public List<PostSearchListResponseDTO> getPosts(StyleSearchRequestDTO styleSearchRequestDTO) {
        List<PostSearchListResponseDTO> posts = postRepository.findByImageStyle(styleSearchRequestDTO);

        return posts;
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
    public PostAndUserDetailDTO getPostDetail(int postNo) {
        PostAndUserDetailDTO postAndUserDetailDTO = postRepository.findPostAndUserByPostNo(postNo);
        if(postAndUserDetailDTO == null) {
            throw new NotExistException(ExceptionCode.NO_EXIST_POST);
        }

        return postAndUserDetailDTO;
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


        return null;
    }
}

