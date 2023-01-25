package cnu.swabe.v0.controller;

import cnu.swabe.v0.domain.Image;
import cnu.swabe.v0.service.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ShareController {
    private final ImageService imageService;

    @PostMapping("/v0/images")
    public int RequestImageInfo(@RequestBody Image image) {
        return imageService.saveImageInfo(image);
    }
}
