package com.Globetrek.dto.Response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CountriesResponseDto {
    @JsonProperty("country_id")
    private Integer countryId;
    @JsonProperty("country_name")
    private String countryName;
}
