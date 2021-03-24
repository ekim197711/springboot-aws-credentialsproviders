package com.example.springbootbucketrenameitem.bucket;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.UploadPartCopyRequest;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class BucketService {
    private final DemoConfig demoConfig;

    public S3Client gimmeClient(AwsCredentialsProvider provider) {

        Region region = Region.EU_CENTRAL_1;
        S3Client s3 = S3Client.builder()
                .region(region)
                .credentialsProvider(provider)
                .build();

        return s3;
    }

    public void uploadFile(AwsCredentialsProvider provider) throws IOException {
        S3Client s3 = gimmeClient(provider);
        String bucketName = "mikes-bucket-of-gems1";
        String remotefilename = "myfile"
                + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH_mm_ss"))
                +".txt";

        InputStream resourceAsStream = BucketService.class.getResourceAsStream("/myfile.txt");

        s3.putObject(PutObjectRequest
                        .builder()
                        .bucket(bucketName)
                        .key(remotefilename)
                        .build(),
                RequestBody.fromInputStream(resourceAsStream, resourceAsStream.available())
                );

        s3.close();
    }

}
