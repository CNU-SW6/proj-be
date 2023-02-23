package cnu.swabe.v2.service;

import cnu.swabe.v2.domain.image.ImageEntity;
import cnu.swabe.v2.domain.image.dto.ImageSaveDTO;
import cnu.swabe.v2.exception.ExceptionCode;
import cnu.swabe.v2.exception.custom.S3Exception;
import cnu.swabe.v2.repository.ImageRepository;
import cnu.swabe.v2.s3.S3Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static cnu.swabe.v2.service.util.ImageServiceUtil.randomAlphabet;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageService {
    private final ImageRepository imageRepository;
    private final S3Service s3Service;
    private final ModelMapper modelMapper = new ModelMapper() {{
        getConfiguration()
                .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE)
                .setFieldMatchingEnabled(true);
    }};

    /**
     * version - v2.1
     * */
    public ImageSaveDTO.Response saveImage(ImageSaveDTO.Request imageSaveRequestDTO) {
        String randomFileName = randomAlphabet(10);
        ImageEntity image = modelMapper.map(imageSaveRequestDTO, ImageEntity.class);
        image.setFileName(randomFileName);
        imageRepository.save(image);
        ImageSaveDTO.Response imageSaveResponseDTO = new ImageSaveDTO.Response(image.getImageNo());

        return imageSaveResponseDTO;
    }

    public String saveImageToS3(int userNo, MultipartFile imageFile) {
        String imageS3Url = null;

        try {
            imageS3Url = s3Service.upload(userNo, imageFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return imageS3Url;
    }

    /**
     * version - v2.1
     * */
    public void deleteImage(int imageNo) {
        imageRepository.deleteByImageNo(imageNo);
    }

    /**
     * version - v2.1
     * */
    public ImageEntity findImageByImageNo(int imageNo) {
        ImageEntity image = imageRepository.findByImageNo(imageNo);
        return image;
    }
}
