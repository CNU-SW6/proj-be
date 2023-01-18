package cnu.swabe.v0.service;

import cnu.swabe.v0.dto.ImageInfoDTO;
import cnu.swabe.v0.dto.StyleDTO;
import cnu.swabe.v0.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ImageService {
    private final ImageRepository imageRepository;

    /**
     * 체크해야할것
     * */
    public List<ImageInfoDTO> getImageInfo(StyleDTO styleDTO) {
        List<ImageInfoDTO> imageInfoDTO = imageRepository.findByStyle(styleDTO);
        return imageInfoDTO;
    }
}
