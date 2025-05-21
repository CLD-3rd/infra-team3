package com.Globetrek.dto.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MypageResponseDto {
    private String nickname;
    private LocalDateTime createdAt;
    private List<PlanDto> plans; // ✅ 좋아요 리스트 제거, 일정만 남김

    // ✅ 일정 조회(Plan 리스트 반환)용
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PlanDto {
        private Integer id;            // WishList의 id (wishlist_id)
        private String countryName;    // country 엔티티의 name (ex: "일본")
        private String countryEmoji;   // country 엔티티에 없으면 null/"" 허용
        private LocalDate travelDate;  // 시작일
        private LocalDate endDate;     // 종료일
    }

    // ✅ 일정 등록(Plan 생성) 요청용 DTO
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PlanRequestDto {
        private String countryName;     // 등록 시 country 이름으로 검색/등록
        private LocalDate travelDate;   // 시작일
        private LocalDate endDate;      // 종료일
    }
}
