package com.example.springbootbucketrenameitem.terraformdemo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BucketUserWithPolicyServiceTest {

    @Autowired
    BucketUserWithPolicyService service;

    @Test
    void createListDeleteObject() throws IOException {
        service.createListDeleteObject("mixedstuff/demo/");
        try {
            service.createListDeleteObject("noaccess/demo/");
            fail("Should get exception");
        }
        catch (S3Exception e){
            System.out.println("Nice we got exception! As expected");
            e.printStackTrace();
        }


    }
}