package com.example.demo.dto;

import lombok.Data;

@Data
public class RelatedPhotoDTO {
    private Long logId;
    private String picUrl;

    public RelatedPhotoDTO(Long logId, String picUrl) {
        this.logId = logId;
        this.picUrl = picUrl;
    }
}