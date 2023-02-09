package cnu.swabe.v2.service;


import cnu.swabe.v2.domain.image.ImageEntity;
import cnu.swabe.v2.domain.image.dto.ImageSaveRequestDTO;
import cnu.swabe.v2.domain.image.dto.ImageSaveResponseDTO;
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
     * Not Null Exception은 UncheckedException으로 그냥 던져주자
     * */
    public ImageSaveResponseDTO saveImage(ImageSaveRequestDTO imageSaveRequestDTO) {
        ImageEntity image = imageRepository.save(imageSaveRequestDTO);
        ImageSaveResponseDTO imageSaveResponse = modelMapper.map(image, ImageSaveResponseDTO.class);
        return imageSaveResponse;
    }

    /**
     * version - v1
     * */

    public void deleteImage(int imageNo) {
        imageRepository.deleteByImageNo(imageNo);
    }
}
