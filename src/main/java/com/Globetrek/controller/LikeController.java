package com.Globetrek.controller;

import com.Globetrek.dto.Response.ErrorResponse;
import com.Globetrek.dto.Response.LikeResponseDto;
import com.Globetrek.service.LikesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/gallery")
public class LikeController {
    private final LikesService likesService;

    @PostMapping("/{log_id}/likes")
    public ResponseEntity<?> toggleLike(@PathVariable("log_id") Integer logId) {
        try {
            // TODO : get JWT userId
            Integer userId = 1;

            // Get current like state
            boolean currentState = likesService.isLiked(userId, logId);
            log.info("Current like state: {}", currentState);

            likesService.toggleLike(userId, logId);

            // Get new like count
            int likeCount = likesService.getLikeCount(logId);
            log.info("New like count: {}", likeCount);

            // Create response DTO with the new state (opposite of current state)
            LikeResponseDto response = new LikeResponseDto();
            response.setLiked(!currentState);
            response.setLikeCount(likeCount);

            log.info("Response DTO: liked={}, likeCount={}", response.isLiked(), response.getLikeCount());

            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            log.error("Error in toggleLike: ", e);
            if (e.getMessage().contains("TL NOT FOUND")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ErrorResponse.notFound("해당 게시물 존재 안함"));
            } else if (e.getMessage().contains("unauthorized")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(ErrorResponse.unauthorized("인증 필요"));
            } else if (e.getMessage().contains("USER NOT FOUND")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ErrorResponse.notFound("해당 USER 존재 안함"));
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ErrorResponse.internalServerError("내부 서버 오류"));
        }
    }
}
