package com.collabnex.controller;

import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.collabnex.service.S3PresignService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Profile("aws")
public class FileUploadController {

    private final S3PresignService s3PresignService;

    /**
     * Generate S3 Presigned URL for uploading file
     * Example:
     * GET /api/files/presigned-url?contentType=image/jpeg
     */
    @GetMapping("/presigned-url")
    public String getPresignedUrl(@RequestParam String contentType) {
        return s3PresignService.generatePresignedUrl(contentType);
    }
}