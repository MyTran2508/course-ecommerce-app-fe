package com.programming.courseservice.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.BucketAccelerateConfiguration;
import com.amazonaws.services.s3.model.BucketAccelerateStatus;
import com.amazonaws.services.s3.model.GetBucketAccelerateConfigurationRequest;
import com.amazonaws.services.s3.model.SetBucketAccelerateConfigurationRequest;
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
    @Value("${application.bucket.name}")
    private String bucketName;
    @Bean
    public AmazonS3 generateS3Client() {
        SecretKey secretKey = secretKeyRepository.findById("AWS_S3").get();
        AWSCredentials credentials = new BasicAWSCredentials(secretKey.getKeySecret(), secretKey.getValueSecret());
        AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(region)
//                .enableAccelerateMode()
                .build();

//        s3Client.setBucketAccelerateConfiguration(
//                new SetBucketAccelerateConfigurationRequest(bucketName,
//                        new BucketAccelerateConfiguration(
//                                BucketAccelerateStatus.Enabled)));
//
//        // Verify that transfer acceleration is enabled for the bucket.
//        String accelerateStatus = s3Client.getBucketAccelerateConfiguration(
//                        new GetBucketAccelerateConfigurationRequest(bucketName))
//                .getStatus();
//        System.out.println("Bucket accelerate status: " + accelerateStatus);

        return s3Client;
    }
}
