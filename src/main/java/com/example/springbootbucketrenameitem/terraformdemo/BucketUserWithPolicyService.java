package com.example.springbootbucketrenameitem.terraformdemo;

import com.example.springbootbucketrenameitem.bucket.DemoConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
@Slf4j
public class BucketUserWithPolicyService {
    private final BucketUserPolicyDemoConfig config;

    public S3Client gimmeClient() {

        Region region = Region.EU_WEST_1;
        return S3Client.builder()
                .region(region)
                .credentialsProvider(
                        StaticCredentialsProvider.create(
                                AwsBasicCredentials.create(
                                        config.getAwsId(),
                                        config.getAwsSecret()
                                )
                        )
                )
                .build();
    }


    public void createListDeleteObject(String prefix) throws IOException {
        S3Client s3Client = gimmeClient();
        String remotefilename = prefix + "myfile"
                + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH_mm_ss"))
                + ".txt";
        putObject(s3Client,remotefilename);

        ListObjectsResponse objects = s3Client.listObjects(
                ListObjectsRequest.builder()
                        .prefix(prefix)
                        .bucket(config.getBucketname())
                        .build()
        );
        objects.contents().forEach(o ->{
            System.out.println(o);
            s3Client.deleteObject(DeleteObjectRequest.builder()
                    .bucket(config.getBucketname())
                    .key(o.key())
                    .build());
                }
        );

        s3Client.close();
    }

    public void deleteObject(String key){
        S3Client s3Client = gimmeClient();
        s3Client.deleteObject(DeleteObjectRequest.builder()
                .bucket(config.getBucketname())
                .key(key)
                .build());
        s3Client.close();
    }


    private void putObject(S3Client s3Client, String remotefilename) throws IOException {
        InputStream resourceAsStream = BucketUserWithPolicyService.class
                .getResourceAsStream("/myfile.txt");
        s3Client.putObject(PutObjectRequest
                        .builder()
                        .bucket(config.getBucketname())
                        .key(remotefilename)
                        .build(),
                RequestBody.fromInputStream(resourceAsStream, resourceAsStream.available())
        );
    }
}
