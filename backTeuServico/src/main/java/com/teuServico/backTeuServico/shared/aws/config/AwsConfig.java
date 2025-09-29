package com.teuServico.backTeuServico.shared.aws.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Configuration;

import java.net.URI;

@Configuration
public class AwsConfig {
    private String endpoint = System.getenv("AWS_ENDPOINT");
    private String awsAcessKey = System.getenv("AWS_ACESS_KEY");
    private  String awsSecret_Key = System.getenv("AWS_SECRET_KEY");

    @Bean
    public S3Client s3Client(){
        AwsBasicCredentials awsBasicCredentials = AwsBasicCredentials.create(awsAcessKey, awsSecret_Key);
        return S3Client.builder()
                .credentialsProvider(StaticCredentialsProvider.create(awsBasicCredentials))
                .forcePathStyle(true)
                .endpointOverride(URI.create(endpoint))
                .region(Region.US_EAST_1)
                .serviceConfiguration(S3Configuration.builder().build())
                .build();
    }

}
