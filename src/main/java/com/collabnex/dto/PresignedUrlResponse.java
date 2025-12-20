package com.collabnex.dto;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class PresignedUrlResponse {
    private String uploadUrl; // S3 PUT URL
    private String fileUrl;  // images/uuid.jpg (RELATIVE PATH)
}




