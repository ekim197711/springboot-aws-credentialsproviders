package com.example.springbootbucketrenameitem;

import com.example.springbootbucketrenameitem.bucket.DemoConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(value = {DemoConfig.class})
public class SpringbootBucketRenameItemApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootBucketRenameItemApplication.class, args);
    }

}
