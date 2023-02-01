package cnu.swabe.v2.service;

import cnu.swabe.v2.domain.image.ImageEntity;
import cnu.swabe.v2.domain.image.dto.ImageRequestDTO;
import cnu.swabe.v2.domain.image.dto.ImageResponseDTO;
import cnu.swabe.v2.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageService {
    private final ImageRepository imageRepository;
    private final ModelMapper modelMapper = new ModelMapper();

    /**
     * version - v2
     * location 없으면 exception
     * */
    public ImageResponseDTO saveImage(ImageRequestDTO imageRequestDTO) {
        ImageEntity image = imageRepository.save(modelMapper.map(imageRequestDTO, ImageEntity.class));
        ImageResponseDTO imageResponse = modelMapper.map(image, ImageResponseDTO.class);
        return imageResponse;
    }

    /**
     * version - v1
     * */
    public void deleteImageInfo(int imageNo) {
        imageRepository.deleteByImageNo(imageNo);
    }
}
