package com.example.springbootbucketrenameitem.bucket;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@SpringBootTest
public class BucketServiceWithDefaultValuesTest {
    @Autowired
    BucketServiceWithDefaultValues bucketService;

    @Test
    public void listBucket(){
        bucketService.listBuckets(Optional.empty());
    }

    @Test
    public void createBucket(){
        bucketService.createDefaultBucket(Optional.empty());
    }

    @Test
    public void listBucketObjects(){
        bucketService.listDefaultBucketContent(Optional.empty());
    }

    @Test
    public void deleteAllFilesDefaultBucket(){
        bucketService.deleteDefaultBucketContent(Optional.empty());
        bucketService.listDefaultBucketContent(Optional.empty());
    }

    @Test
    public void uploadFile() throws IOException {
        String localFileName = "myfileforunittests.txt";
        String remoteFileName = LocalDateTime.now().format(DateTimeFormatter.ofPattern("/yyyy/MM/dd/HH/mm/")) + localFileName;
        InputStream resourceAsStream = BucketServiceWithDefaultValuesTest.class.getResourceAsStream("/" + localFileName);
        long numberOfBytes = resourceAsStream.available();
        bucketService.uploadFileToDefaultBucket(resourceAsStream, numberOfBytes, remoteFileName, Optional.empty());
        bucketService.listDefaultBucketContent(Optional.empty());
    }



}
