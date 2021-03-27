package com.example.springbootbucketrenameitem.bucket;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class BucketServiceWithDefaultValues {

    public S3Client gimmeClient(Optional<AwsCredentialsProvider> provider) {
        Region region = Region.EU_CENTRAL_1;
        return S3Client.builder()
                .region(region)
                .credentialsProvider(provider
                        .orElse(DefaultCredentialsProvider.create()))
                .build();
    }

    final String DEFAULT_BUCKET_NAME = "mikes-default-bucket-demo";
    public List<Bucket> listBuckets(Optional<AwsCredentialsProvider> provider) {
        S3Client client = gimmeClient(provider);

        ListBucketsResponse listBucketsResponse = client.listBuckets();
        for (Bucket bucket : listBucketsResponse.buckets()) {
            log.info("Bucket: {}", bucket.name());
        }
        client.close();
        return listBucketsResponse.buckets();
    }
        public void createDefaultBucket(Optional<AwsCredentialsProvider> provider) {

        S3Client client = gimmeClient(provider);

        ListBucketsResponse listBucketsResponse = client.listBuckets();
        boolean exists = listBucketsResponse
                .buckets()
                .stream()
                .anyMatch(b -> b.name().equals(DEFAULT_BUCKET_NAME));
        if (!exists) {
            log.info("Create bucket!");
            CreateBucketResponse bucket = client.createBucket(CreateBucketRequest
                    .builder()
                    .bucket(DEFAULT_BUCKET_NAME)
                    .build());
            log.info("Create bucket Done - Result:{}", bucket.toString());
        } else {
            log.info("Bucket already exists!");
        }
        client.close();
    }

    public void uploadFileToDefaultBucket(InputStream resourceAsStream,
                                          long numberOfBytes,
                                          String remoteFileName,
                                          Optional<AwsCredentialsProvider> empty) {
        S3Client s3Client = gimmeClient(empty);
        s3Client.putObject(PutObjectRequest
                        .builder()
                        .bucket(DEFAULT_BUCKET_NAME)
                        .key(remoteFileName)
                        .build(),
                RequestBody.fromInputStream(resourceAsStream, numberOfBytes)
        );

        s3Client.close();
    }

    public void deleteDefaultBucketContent(Optional<AwsCredentialsProvider> empty) {
        S3Client s3Client = gimmeClient(empty);
        ListObjectsResponse response = s3Client.listObjects(ListObjectsRequest
                .builder()
                .bucket(DEFAULT_BUCKET_NAME)
                .build());

        response.contents().forEach(s3Object ->
                s3Client.deleteObject(
                        DeleteObjectRequest.builder()
                                .bucket(DEFAULT_BUCKET_NAME)
                                .key(s3Object.key())
                                .build()));

        s3Client.close();
    }
    public void listDefaultBucketContent(Optional<AwsCredentialsProvider> empty) {
        S3Client s3Client = gimmeClient(empty);
        ListObjectsResponse response = s3Client.listObjects(ListObjectsRequest
                .builder()
                .bucket(DEFAULT_BUCKET_NAME)
                .build());

        response.contents().forEach(c ->
                log.info("Bucket object: {}", c.key()));

        s3Client.close();
    }
}
