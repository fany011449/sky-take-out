package com.sky.config;

import com.sky.properties.CloudFlareR2Properties;
import com.sky.utils.CloudFlareR2Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

import java.net.URI;

/**
 * 配置類
 * 用於創建 S3Client 對象
 */
@Configuration
@EnableConfigurationProperties(CloudFlareR2Properties.class)
@Slf4j
public class CloudFlareR2Configuration {

    @Bean
    @ConditionalOnMissingBean
    public S3Client createS3Client(CloudFlareR2Properties cloudFlareR2Properties) {

        log.info("開始創建CloudFlareR2文件上傳工具類對象: {}", cloudFlareR2Properties);

        return S3Client.builder()
                .endpointOverride(URI.create(cloudFlareR2Properties.getEndpoint()))
                .region(Region.of("auto"))
                .credentialsProvider(StaticCredentialsProvider
                        .create(AwsBasicCredentials.create(
                                cloudFlareR2Properties.getAwsAccessKeyId(), cloudFlareR2Properties.getAwsSecretAccessKey())))
                .build();
    }
}
