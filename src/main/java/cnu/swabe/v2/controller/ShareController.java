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

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/api/images")
    public SuccessResponse<String> RequestSaveImage(int userNo, MultipartFile image) {
        log.info("SaveImage::: userNo={}, fileName={}", userNo, image.getOriginalFilename());

        String imageS3Url = imageService.saveImageToS3(userNo, image);

        return new SuccessResponse(imageS3Url);
    }


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/api/posts")
    public SuccessResponse<PostSaveDTO.Response> RequestSavePost(@RequestBody PostSaveDTO.Request postSaveRequestDTO) {
        log.info("SavePost::: userNo={}, location={}, hatColor={}, topColor={}, pantsColor={}, shoesColor={}, isMale={}, description={}, isSell={}",
                postSaveRequestDTO.getUserNo(),
                postSaveRequestDTO.getLocation(),
                postSaveRequestDTO.getHatColor(),
                postSaveRequestDTO.getTopColor(),
                postSaveRequestDTO.getPantsColor(),
                postSaveRequestDTO.getShoesColor(),
                postSaveRequestDTO.isMale(),
                postSaveRequestDTO.getDescription(),
                postSaveRequestDTO.isSell()
        );

        PostSaveDTO.Response postSaveResponseDTO = postService.savePost(postSaveRequestDTO);

        return new SuccessResponse(postSaveResponseDTO);
    }
}
