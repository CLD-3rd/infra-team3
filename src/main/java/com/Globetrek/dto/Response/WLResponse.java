package com.Globetrek.dto.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WLResponse<T> {
    private T data;
    private PageInfo pageInfo;

    public static <T> WLResponse<T> success(T data) {
        return WLResponse.<T>builder()
                .data(data)
                .build();
    }

    public static <T> WLResponse<T> success(T data, PageInfo pageInfo) {
        return WLResponse.<T>builder()
                .data(data)
                .pageInfo(pageInfo)
                .build();
    }
}
