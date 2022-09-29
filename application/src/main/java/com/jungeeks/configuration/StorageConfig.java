package com.jungeeks.configuration;

import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StorageConfig {

    @Bean
    public AmazonS3 amazonS3(@Value("${STORAGE_PROVIDER}") STORAGE_PROVIDER_ENUM storageProvider, @Value("${STORAGE_REGION}") String region) {
        EnvironmentVariableCredentialsProvider envProvider = new EnvironmentVariableCredentialsProvider();
        return switch (storageProvider) {
            case AWS -> AmazonS3ClientBuilder.standard()
                        .withCredentials(envProvider)
                        .withRegion(region)
                        .build();

            case YANDEX -> AmazonS3ClientBuilder.standard()
                        .withCredentials(envProvider)
                        .withEndpointConfiguration(
                                new AmazonS3ClientBuilder.EndpointConfiguration(
                                        "storage.yandexcloud.net", "ru-central1"
                                )
                        )
                        .build();
        };
    }
}