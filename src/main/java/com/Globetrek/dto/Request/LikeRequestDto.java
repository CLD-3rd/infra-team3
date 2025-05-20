package com.Globetrek.dto.Request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class LikeRequestDto {
    private boolean isLiked;
    private int likeCount;
}
