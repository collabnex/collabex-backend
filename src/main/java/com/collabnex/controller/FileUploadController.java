package com.collabnex.controller;

import com.collabnex.dto.PresignedUrlResponse;
import com.collabnex.service.S3PresignService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class FileUploadController {

    private final S3PresignService s3PresignService;

    @GetMapping("/presigned-url")
    public PresignedUrlResponse getPresignedUrl(
            @RequestParam String contentType
    ) {
        return s3PresignService.generatePresignedUrl(contentType);
    }
}

