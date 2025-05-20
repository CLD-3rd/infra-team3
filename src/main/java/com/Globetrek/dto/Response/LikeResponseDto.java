package com.Globetrek.dto.Response;

import lombok.Data;

@Data
public class LikeResponseDto {
    private boolean liked;
    private int likeCount;
}