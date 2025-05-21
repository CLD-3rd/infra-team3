package com.Globetrek.controller;

import com.Globetrek.dto.Response.ErrorResponse;
import com.Globetrek.dto.Response.LikeResponseDto;
import com.Globetrek.dto.security.LoginDetails;
import com.Globetrek.service.LikesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/gallery")
public class LikeController {
    private final LikesService likesService;

    @PostMapping("/{logId}/likes")
    public ResponseEntity<?> toggleLike(@PathVariable Integer logId,
                                      @AuthenticationPrincipal LoginDetails loginDetails) {
        try {
            if (loginDetails == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(ErrorResponse.unauthorized("로그인이 필요합니다."));
            }

            LikeResponseDto response = likesService.toggleLike(logId, loginDetails.getUser().getId());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            if (e.getMessage().contains("NOT FOUND")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ErrorResponse.notFound("해당 게시물이 존재하지 않습니다."));
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ErrorResponse.internalServerError("내부 서버 오류"));
        }
    }

    @GetMapping("/{logId}/likes")
    public ResponseEntity<?> getLikeStatus(@PathVariable Integer logId,
                                         @AuthenticationPrincipal LoginDetails loginDetails) {
        try {
            if (loginDetails == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(ErrorResponse.unauthorized("로그인이 필요합니다."));
            }

            LikeResponseDto response = likesService.getLikeStatus(logId, loginDetails.getUser().getId());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            if (e.getMessage().contains("NOT FOUND")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ErrorResponse.notFound("해당 게시물이 존재하지 않습니다."));
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ErrorResponse.internalServerError("내부 서버 오류"));
        }
    }
}
