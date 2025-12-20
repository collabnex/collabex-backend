package com.collabnex.service;

import java.time.Duration;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

@Service
@Profile("aws") 
public class S3PresignService {

    private final S3Presigner presigner;

    @Value("${aws.s3.bucket}")
    private String bucket;

    @Value("${aws.s3.presign-expiry-minutes}")
    private long expiryMinutes;

    public S3PresignService(S3Presigner presigner) {
        this.presigner = presigner;
    }

    // ✅ THIS METHOD MUST EXIST
    public String generatePresignedUrl(String contentType) {

        String key = "images/" + UUID.randomUUID() + ".jpg";

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .contentType(contentType)
                .build();

        PutObjectPresignRequest presignRequest =
                PutObjectPresignRequest.builder()
                        .signatureDuration(Duration.ofMinutes(expiryMinutes))
                        .putObjectRequest(putObjectRequest)
                        .build();

        PresignedPutObjectRequest presignedRequest =
                presigner.presignPutObject(presignRequest);

        return presignedRequest.url().toString();
    }
}




