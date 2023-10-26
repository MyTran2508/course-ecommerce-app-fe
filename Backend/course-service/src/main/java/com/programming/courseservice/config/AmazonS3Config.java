package com.programming.courseservice.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.programming.courseservice.domain.persistent.entity.SecretKey;
import com.programming.courseservice.repository.SecretKeyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmazonS3Config {
    @Autowired
    SecretKeyRepository secretKeyRepository;
    private String accessKey;
    private String accessSecret;
    @Value("${cloud.aws.region.static}")
    private String region;
    @Bean
    public AmazonS3 generateS3Client() {
        SecretKey secretKey = secretKeyRepository.findById("AWS_S3").get();
        AWSCredentials credentials = new BasicAWSCredentials(secretKey.getKeySecret(), secretKey.getValueSecret());
        return AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(region)
                .build();
    }
}
