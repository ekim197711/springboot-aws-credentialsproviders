package com.example.springbootbucketrenameitem.bucket;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import software.amazon.awssdk.auth.credentials.*;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class BucketServiceTest {

    @Autowired
    BucketService bucketService;

    @Autowired
    DemoConfig demoConfig;

    @Test
    void uploadFileStatic() throws IOException {
        AwsBasicCredentials awsBasicCredentials = AwsBasicCredentials
                .create(demoConfig.getAccessid(), demoConfig.getSecretkey());
        StaticCredentialsProvider staticCredentialsProvider = StaticCredentialsProvider.create(awsBasicCredentials);
        bucketService.uploadFile(staticCredentialsProvider);
    }

    @Test
    void uploadFileNoPermissions() throws IOException {
        ProfileCredentialsProvider nopermissionsProfile = ProfileCredentialsProvider
                .builder()
                .profileName("nopermissions")
                .build();
        bucketService.uploadFile(nopermissionsProfile);
    }

    @Test
    void uploadFileOnlyBucketProfile() throws IOException {
        ProfileCredentialsProvider bucketDemo = ProfileCredentialsProvider
                .builder()
                .profileName("onlybucket")
                .build();
        bucketService.uploadFile(bucketDemo);

    }

    @Test
    void uploadFileEnvironmentVariable() throws IOException {
        EnvironmentVariableCredentialsProvider envVar = EnvironmentVariableCredentialsProvider.create();
        bucketService.uploadFile(envVar);
    }
    @Test
    public void uploadFileJavaSystem() throws IOException {
        SystemPropertyCredentialsProvider envVar = SystemPropertyCredentialsProvider.create();
        bucketService.uploadFile(envVar);
    }

    @Test
    void uploadFileDefaultProfile() throws IOException {
        DefaultCredentialsProvider defaultProfile = DefaultCredentialsProvider
                .builder()
                .build();
        bucketService.uploadFile(defaultProfile);
    }
}