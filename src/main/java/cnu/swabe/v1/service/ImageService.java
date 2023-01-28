package cnu.swabe.v1.service;

import cnu.swabe.v1.domain.Image;
import cnu.swabe.v1.dto.ImageInfoDTO;
import cnu.swabe.v1.dto.StyleDTO;
import cnu.swabe.v1.repository.ImageRepository;
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
     * */
    public List<ImageInfoDTO> getImageInfo(StyleDTO styleDTO) {
        List<ImageInfoDTO> imageInfoDTO = imageRepository.findByStyle(styleDTO);
        return imageInfoDTO;
    }


    /**
     * version - v1
     * 색 코드가 하나도 없으면 exception?
     * */
    public Image saveImageInfo(Image imageDTO) {
        Image image = imageRepository.save(imageDTO);
        return image;
    }
}
