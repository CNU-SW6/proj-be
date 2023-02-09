package cnu.swabe.v2.controller;

import cnu.swabe.v2.domain.image.Image;
import cnu.swabe.v2.domain.Post;
import cnu.swabe.v2.domain.image.dto.ImageSaveRequestDTO;
import cnu.swabe.v2.domain.image.dto.ImageSaveResponseDTO;
import cnu.swabe.v2.domain.post.PostEntity;
import cnu.swabe.v2.domain.post.dto.PostSaveRequestDTO;
import cnu.swabe.v2.domain.post.dto.PostSaveResponseDTO;
import cnu.swabe.v2.response.SuccessResponse;
import cnu.swabe.v2.service.ImageService;
import cnu.swabe.v2.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ShareController {
    private final ImageService imageService;
    private final PostService postService;

    @ResponseStatus(HttpStatus.CREATED)

    @PostMapping("/api/images")
    public SuccessResponse RequestImageInfo(@RequestBody Image imageDTO) {
        log.info("??? userNo={}, location={}, hatColor={}, topColor={}, pantsColor={}, shoesColor={}",
                imageDTO.getUserNo(),
                imageDTO.getLocation(),
                imageDTO.getHatColor(),
                imageDTO.getTopColor(),
                imageDTO.getPantsColor(),
                imageDTO.getShoesColor()
        );

        Image image = imageService.saveImageInfo(imageDTO);

        return new SuccessResponse(image);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/api/posts")
    public SuccessResponse RequestPostInfo(@RequestBody Post postDTO) {
        log.info("??? userNo={}, imageNo={}, description={}, isSell={}, setUrl={}",
                postDTO.getUserNo(),
                postDTO.getImageNo(),
                postDTO.getDescription(),
                postDTO.isSell(),
                postDTO.isSell()
        );

        Post post = postService.savePostInfo(postDTO);
        return new SuccessResponse(post);

    @PostMapping("/v2/images")
    public SuccessResponse<ImageSaveResponseDTO> RequestSaveImage(@RequestBody ImageSaveRequestDTO imageSaveRequestDTO) {
        log.info("SaveImage::: userNo={}, location={}, hatColor={}, topColor={}, pantsColor={}, shoesColor={}",
                imageSaveRequestDTO.getUserNo(),
                imageSaveRequestDTO.getLocation(),
                imageSaveRequestDTO.getHatColor(),
                imageSaveRequestDTO.getTopColor(),
                imageSaveRequestDTO.getPantsColor(),
                imageSaveRequestDTO.getShoesColor()
        );

        ImageSaveResponseDTO imageSaveResponse = imageService.saveImage(imageSaveRequestDTO);

        return new SuccessResponse(imageSaveResponse);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/v2/posts")
    public SuccessResponse<PostSaveResponseDTO> RequestSavePost(@RequestBody PostSaveRequestDTO postSaveRequestDTO) {
        log.info("SavePost::: userNo={}, imageNo={}, description={}, isSell={}, sellUrl={}",
                postSaveRequestDTO.getUserNo(),
                postSaveRequestDTO.getImageNo(),
                postSaveRequestDTO.getDescription(),
                postSaveRequestDTO.isSell(),
                postSaveRequestDTO.isSell()
        );

        PostSaveResponseDTO postSaveResponse = postService.savePost(postSaveRequestDTO);
        return new SuccessResponse(postSaveResponse);

    }
}
