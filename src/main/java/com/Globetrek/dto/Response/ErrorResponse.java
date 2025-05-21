package com.Globetrek.dto.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    private String error;
    private String msg;

    public static ErrorResponse unauthorized(String message) {
        return ErrorResponse.builder()
                .error("Unauthorized")
                .msg(message)
                .build();
    }

    public static ErrorResponse forbidden(String message) {
        return ErrorResponse.builder()
                .error("Forbidden")
                .msg(message)
                .build();
    }

    public static ErrorResponse notFound(String message) {
        return ErrorResponse.builder()
                .error("Not found")
                .msg(message)
                .build();
    }

    public static ErrorResponse badRequest(String message) {
        return ErrorResponse.builder()
                .error("Bad request")
                .msg(message)
                .build();
    }

    public static ErrorResponse conflict(String message) {
        return ErrorResponse.builder()
                .error("Conflict")
                .msg(message)
                .build();
    }

    public static ErrorResponse unprocessableEntity(String message) {
        return ErrorResponse.builder()
                .error("Unprocessable entity")
                .msg(message)
                .build();
    }

    public static ErrorResponse internalServerError(String message) {
        return ErrorResponse.builder()
                .error("Internal server error")
                .msg(message)
                .build();
    }
}