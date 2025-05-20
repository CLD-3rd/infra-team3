package com.Globetrek.dto.Request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class WishListRequestDto {
    private Integer wishlistId;
    private Integer countryId;
    private String countryName;
    private String flagUrl;
    private LocalDate travelDate;
    private LocalDate endDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}