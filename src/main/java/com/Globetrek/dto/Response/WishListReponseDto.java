package com.Globetrek.dto.Response;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class WishListReponseDto {
    private Integer wishlistId;
    private Integer countryId;
    private String countryName;
    private String flagUrl;
    private LocalDate travelDate;
    private LocalDate endDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}