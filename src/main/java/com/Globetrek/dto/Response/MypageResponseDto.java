package com.Globetrek.dto.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MypageResponseDto {
    private String nickname;
    private LocalDateTime createdAt;

    private List<CommentResponseDto> comments;
    private List<PlanDto> plans;
    private List<CountryDto> countries;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PlanDto {
        private Integer id;
        private String countryName;
        private String countryEmoji;
        private String travelDate;
        private String endDate;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CountryDto {
        private String name;
        private String emoji;
    }

    // 여기서 static 내부 클래스로 PlanRequestDto 선언
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PlanRequestDto {
        private String countryName;
        private String travelDate;
        private String endDate;
    }
}
