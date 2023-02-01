package cnu.swabe.v2.service;

import cnu.swabe.v2.domain.image.Image;
import cnu.swabe.v2.domain.image.dto.ImageInfoDTO;
import cnu.swabe.v2.domain.image.dto.ImageStyleDTO;
import cnu.swabe.v2.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageService {
    private final ImageRepository imageRepository;

    /**
     * version - v1
     * 색 코드가 하나도 없으면 exception?
     * */
    public Image saveImageInfo(Image imageDTO) {
        Image image = imageRepository.save(imageDTO);
        return image;
    }

    /**
     * version - v1
     * */
    public void deleteImageInfo(int imageNo) {
        imageRepository.deleteByImageNo(imageNo);
    }
}
