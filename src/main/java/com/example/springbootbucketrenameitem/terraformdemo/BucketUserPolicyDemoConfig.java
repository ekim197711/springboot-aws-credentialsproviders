package com.example.springbootbucketrenameitem.terraformdemo;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("policy-demo")
@Data
public class BucketUserPolicyDemoConfig {
    private String awsId;
    private String awsSecret;
    private String bucketname;
}
