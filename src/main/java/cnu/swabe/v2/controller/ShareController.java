package cnu.swabe.v2.controller;

import cnu.swabe.v2.domain.image.Image;
import cnu.swabe.v2.domain.post.PostEntity;
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
    @PostMapping("/v1/images")
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
    @PostMapping("/v1/posts")
    public SuccessResponse RequestPostInfo(@RequestBody PostEntity postEntityDTO) {
        log.info("??? userNo={}, imageNo={}, description={}, isSell={}, setUrl={}",
                postEntityDTO.getUserNo(),
                postEntityDTO.getImageNo(),
                postEntityDTO.getDescription(),
                postEntityDTO.isSell(),
                postEntityDTO.isSell()
        );

        PostEntity postEntity = postService.savePostInfo(postEntityDTO);
        return new SuccessResponse(postEntity);
    }
}
