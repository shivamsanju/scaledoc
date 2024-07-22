package com.shvmsnju.scaledoc_project_service.service;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import jakarta.annotation.PostConstruct;

@Service
public class FileService {
    private Logger LOG = LoggerFactory.getLogger(FileService.class);

    @Autowired
    private MinioClient minioClient;

    @Value("${minio.bucketName}")
    private String bucketName;

    @PostConstruct
    public void createBucketIfNotPresent() {
        try {
            if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build())) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            }
            LOG.info(bucketName + " bucket created");
        } catch (Exception e) {
            LOG.error(bucketName + " bucket creation failed", e);
            throw new RuntimeException(e);
        }
    }

    public String uploadFile(MultipartFile file) {
        try {
            String uniqueBlobString = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            LOG.info(String.format("Saving file=%s to S3", uniqueBlobString));
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(uniqueBlobString)
                    .stream(file.getInputStream(), file.getSize(), -1)
                    .contentType(file.getContentType()).build());
            return uniqueBlobString;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void removeFile(String blobString) {
        try {
            LOG.info(String.format("Removing file=%s from S3", blobString));
            minioClient.removeObject(RemoveObjectArgs.builder().bucket(bucketName).object(blobString).build());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
