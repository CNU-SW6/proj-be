package cnu.swabe.v2.controller;

import cnu.swabe.v2.domain.image.dto.ImageSaveRequestDTO;
import cnu.swabe.v2.domain.image.dto.ImageSaveResponseDTO;
import cnu.swabe.v2.domain.post.dto.PostSaveDTO;
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
    public SuccessResponse<PostSaveDTO.ResponseDTO> RequestSavePost(@RequestBody PostSaveDTO.RequestDTO postSaveRequestDTO) {
        log.info("SavePost::: userNo={}, imageNo={}, description={}, isSell={}, sellUrl={}",
                postSaveRequestDTO.getUserNo(),
                postSaveRequestDTO.getImageNo(),
                postSaveRequestDTO.getDescription(),
                postSaveRequestDTO.isSell(),
                postSaveRequestDTO.isSell()
        );

        PostSaveDTO.ResponseDTO postSaveResponse = postService.savePost(postSaveRequestDTO);
        return new SuccessResponse(postSaveResponse);
    }
}
