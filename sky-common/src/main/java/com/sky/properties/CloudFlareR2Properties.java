package com.sky.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "sky.r2")
@Data
public class CloudFlareR2Properties {

    private String endpoint;
    private String bucketName;
    private String publicUrl;
    private String awsAccessKeyId;
    private String awsSecretAccessKey;
}
