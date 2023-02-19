package cnu.swabe.v2.controller;

import cnu.swabe.v2.domain.post.dto.PostSaveDTO;
import cnu.swabe.v2.response.SuccessResponse;
import cnu.swabe.v2.s3.S3Service;
import cnu.swabe.v2.service.ImageService;
import cnu.swabe.v2.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ShareController {
    private final ImageService imageService;
    private final PostService postService;
    private final S3Service s3Service;


    /*
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/v2.1/images")
    public SuccessResponse<ImageSaveDTO.Request> RequestSaveImage(@RequestBody ImageSaveDTO.Request imageSaveRequestDTO) {
        log.info("SaveImage::: userNo={}, location={}, hatColor={}, topColor={}, pantsColor={}, shoesColor={}",
                imageSaveRequestDTO.getUserNo(),
                imageSaveRequestDTO.getLocation(),
                imageSaveRequestDTO.getHatColor(),
                imageSaveRequestDTO.getTopColor(),
                imageSaveRequestDTO.getPantsColor(),
                imageSaveRequestDTO.getShoesColor()
        );

        ImageSaveDTO.Response imageSaveResponseDTO = imageService.saveImage(imageSaveRequestDTO);

        return new SuccessResponse(imageSaveResponseDTO);
    }
    */

    /*
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/v2.1/posts")
    public SuccessResponse<PostSaveDTO.Response> RequestSavePost(@RequestBody PostSaveDTO.Request postSaveRequestDTO) throws IOException {
        log.info("SavePost::: userNo={}, imageFile={}, hatColor={}, topColor={}, pantsColor={}, shoesColor={}, isMale={}, description={}, isSell={}, sellUrl={}",
                postSaveRequestDTO.getUserNo(),
                postSaveRequestDTO.getImageFile().getOriginalFilename(),
                postSaveRequestDTO.getHatColor(),
                postSaveRequestDTO.getTopColor(),
                postSaveRequestDTO.getPantsColor(),
                postSaveRequestDTO.getShoesColor(),
                postSaveRequestDTO.isMale(),
                postSaveRequestDTO.getDescription(),
                postSaveRequestDTO.isSell(),
                postSaveRequestDTO.isSell()
        );

        s3Service.upload(postSaveRequestDTO.getImageFile());

        PostSaveDTO.Response postSaveResponseDTO = postService.savePost(postSaveRequestDTO);

        return new SuccessResponse(postSaveResponseDTO);
    }
    */

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/v2.1/posts")
    public SuccessResponse<PostSaveDTO.Response> RequestSavePost(MultipartFile multipartFile) throws IOException {
        log.info(multipartFile.getOriginalFilename());

        s3Service.upload(multipartFile);


        return new SuccessResponse();
    }
}
