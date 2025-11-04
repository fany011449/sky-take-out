package com.sky.utils;

import com.sky.properties.CloudFlareR2Properties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Slf4j
@Component
public class CloudFlareR2Util {

    // 藉由CloudFlareR2Configuration依賴灌入
    @Autowired
    private S3Client s3Client;

    @Autowired
    private CloudFlareR2Properties cloudFlareR2Properties;

    /**
     * 文件上傳
     * @param file
     * @return
     */
    public String upload(MultipartFile file) {

        // 調整上傳名稱為UUID，防止重複名稱
        String originalFilename = file.getOriginalFilename(); // 原始文件名稱
        // 擷取文件副檔名
        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf(".")); // 從最後.開始擷取
        String dir = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM"));
        String objectName = dir + "/" + fileExtension;

        // 上傳文件至 CloudFlareR2
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(cloudFlareR2Properties.getBucketName())
                .key(objectName)
                .contentType(file.getContentType())
                .build();
        try {
            s3Client.putObject(putObjectRequest, RequestBody.fromBytes(file.getBytes()));
            log.info("✅ 上傳成功：%s -> {}{}", file.getOriginalFilename(), objectName);
        } catch (IOException e) {
           log.info("Error Message ： {}",e.getMessage());
        } finally {
            if(s3Client != null) {
                s3Client.close();
            }
        }

        // 文件公開路徑：https://pub-7db6ef2124f14e9d814f5a5f35ebb710.r2.dev/objectName
        StringBuilder stringBuilder = new StringBuilder("https://");
        stringBuilder
                .append(cloudFlareR2Properties.getPublicUrl())
                .append(".r2.dev/")
                .append(objectName);

        log.info("文件公開至：{}", stringBuilder);
        return stringBuilder.toString();
    }
}
