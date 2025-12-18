package com.collabnex.dto;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class PresignedUrlResponse {
    private String uploadUrl;
    private String fileUrl;
}
