package com.example.demo.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TravelLogDTO {
    private String picUrl;
    private String title;
    private int likeCount;
    private LocalDateTime createdAt;
    private Long logId;
    private boolean isLiked;
    private boolean isWished;
    private Long countryId;
    private String countryName;
}