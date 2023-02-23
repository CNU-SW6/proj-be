package cnu.swabe.v2.s3;

import cnu.swabe.v2.exception.ExceptionCode;
import cnu.swabe.v2.exception.custom.S3Exception;
import cnu.swabe.v2.service.util.ImageServiceUtil;
import com.amazonaws.HttpMethod;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Date;

@Service
@NoArgsConstructor
public class S3Service {
    private AmazonS3 s3Client;
    private final String endPoint = "https://kr.object.ncloudstorage.com";

    @Value("${cloud.aws.credentials.accessKey}")
    private String accessKey;

    @Value("${cloud.aws.credentials.secretKey}")
    private String secretKey;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.region.static}")
    private String region;

    @PostConstruct
    public void setS3Client() {
        s3Client = AmazonS3ClientBuilder.standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(endPoint, region))
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey)))
                .build();
    }

    public Date getExpirationTime() {
        // set expiration
        Date expiration = new Date();
        long expTimeMillis = expiration.getTime();
        expTimeMillis += 1000 * 60 * 60; // 1 hour
        expiration.setTime(expTimeMillis);
        return expiration;
    }

    public String makeFileName(int userNo, String originFileName) {
        StringBuilder sb = new StringBuilder();
        sb.append(userNo);
        sb.append("_");
        sb.append(originFileName);
        sb.append("_");
        sb.append(ImageServiceUtil.randomAlphabet(3));
        return sb.toString();
    }

    public String upload(int userNo, MultipartFile file) throws IOException {
        String fileName = makeFileName(userNo, file.getOriginalFilename());
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());

        try {
            s3Client.putObject(new PutObjectRequest(bucket, fileName, file.getInputStream(), metadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (AmazonS3Exception e) {
            throw new S3Exception(ExceptionCode.PROBLEM_BY_S3_SERVER);
        } catch (SdkClientException e) {
            throw new S3Exception(ExceptionCode.PROBLEM_BY_S3_CLIENT);
        }

        /*
        GeneratePresignedUrlRequest generatePresignedUrlRequest =
                new GeneratePresignedUrlRequest(bucket, fileName)
                        .withMethod(HttpMethod.GET)
                        .withExpiration(getExpirationTime());

        return s3Client.generatePresignedUrl(generatePresignedUrlRequest).toString();
        */
        return s3Client.getUrl(bucket, fileName).toString();
    }

    public void delete(String objectName) {
        try {
            s3Client.deleteObject(bucket, objectName);
        } catch (AmazonS3Exception e) {
            throw new S3Exception(ExceptionCode.PROBLEM_BY_S3_SERVER);
        } catch (SdkClientException e) {
            throw new S3Exception(ExceptionCode.PROBLEM_BY_S3_CLIENT);
        }
    }
}
