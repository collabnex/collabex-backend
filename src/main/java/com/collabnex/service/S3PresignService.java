package com.collabnex.service;

import com.collabnex.dto.PresignedUrlResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

import java.time.Duration;
import java.util.UUID;

@Service
public class S3PresignService {

    private final S3Presigner presigner;

    @Value("${aws.s3.bucket}")
    private String bucket;

    @Value("${aws.region}")
    private String region;

    @Value("${aws.s3.presign-expiry-minutes}")
    private long expiryMinutes;

    public S3PresignService(S3Presigner presigner) {
        this.presigner = presigner;
    }

    // ✅ FIXED METHOD
    public PresignedUrlResponse generatePresignedUrl(String contentType) {

        // 1️⃣ Generate RELATIVE PATH (THIS GOES IN DB)
        String key = "images/" + UUID.randomUUID() + ".jpg";

        // 2️⃣ Create PUT request
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .contentType(contentType)
                .build();

        // 3️⃣ Presign
        PutObjectPresignRequest presignRequest =
                PutObjectPresignRequest.builder()
                        .signatureDuration(Duration.ofMinutes(expiryMinutes))
                        .putObjectRequest(putObjectRequest)
                        .build();

        PresignedPutObjectRequest presignedRequest =
                presigner.presignPutObject(presignRequest);

        // 4️⃣ Public file URL (for preview)
        String fileUrl = "https://" + bucket + ".s3." + region + ".amazonaws.com/" + key;

        return new PresignedUrlResponse(
                presignedRequest.url().toString(), // uploadUrl
                key                                // images/uuid.jpg
        );
    }
}
