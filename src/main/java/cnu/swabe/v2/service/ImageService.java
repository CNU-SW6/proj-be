package cnu.swabe.v2.service;


import cnu.swabe.v2.domain.image.ImageEntity;
import cnu.swabe.v2.domain.image.dto.ImageSaveDTO;
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
     * version - v2.1
     * Not Null Exception은 UncheckedException으로 그냥 던져주자
     * */
    public ImageSaveDTO.Response saveImage(ImageSaveDTO.Request imageSaveRequestDTO) {
        ImageEntity image = modelMapper.map(imageSaveRequestDTO, ImageEntity.class);
        imageRepository.save(image);
        ImageSaveDTO.Response imageSaveResponseDTO = modelMapper.map(image, ImageSaveDTO.Response.class);
        return imageSaveResponseDTO;
    }

    /**
     * version - v1
     * */
    public void deleteImage(int imageNo) {
        imageRepository.deleteByImageNo(imageNo);
    }
}
