package com.Globetrek.controller;

import com.Globetrek.dto.Request.CommentRequestDto;
import com.Globetrek.dto.Response.CommentResponseDto;
import com.Globetrek.dto.Response.ErrorResponse;
import com.Globetrek.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/gallery")
public class CommentController {
    private final CommentService commentService;

    @GetMapping("/{log_Id}/comments")
    public ResponseEntity<?> getAllComments(@PathVariable Integer log_Id) {
        try {
            return ResponseEntity.ok(commentService.getAllComments(log_Id));
        } catch (Exception e) {
            if (e.getMessage().contains("TL NOT FOUND")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ErrorResponse.notFound("해당 TL 존재 안함"));
            } else if (e.getMessage().contains("USER NOT FOUND")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ErrorResponse.notFound("해당 USER 존재 안함"));
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ErrorResponse.internalServerError("내부 오류"));
        }
    }

    @PostMapping("/{logId}/comments")
    public ResponseEntity<?> createComment(@PathVariable Integer logId,
                                           @RequestBody CommentRequestDto commentRequestDto,
                                           @RequestHeader(value = "Authorization", required = false) String token) {
        try {
            // TODO : get JWT userId
            Integer userId = 1;
            
            // JWT 토큰이 있는 경우 토큰에서 userId 추출
            /*
            if (token != null && token.startsWith("Bearer ")) {
                String jwtToken = token.substring(7);
                Claims claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(jwtToken)
                    .getBody();
                userId = Long.parseLong(claims.getSubject());
            }
            */

            CommentResponseDto created = commentService.createComment(logId, userId, commentRequestDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (Exception e) {
            if (e.getMessage().contains("TL NOT FOUND")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ErrorResponse.notFound("해당 TL 존재 안함"));
            } else if (e.getMessage().contains("USER NOT FOUND")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ErrorResponse.notFound("해당 USER 존재 안함"));
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ErrorResponse.internalServerError("내부 오류"));
        }
    }

    @PutMapping("/{logId}/comments/{commentId}")
    public ResponseEntity<?> updateComment(@PathVariable Integer logId,
                                           @PathVariable Integer commentId,
                                           @RequestBody CommentRequestDto commentRequestDto,
                                           @RequestHeader(value = "Authorization", required = false) String token) {
        try {
            // TODO : get JWT userId
            Integer userId = 1;
            
            // JWT 토큰이 있는 경우 토큰에서 userId 추출
            /*
            if (token != null && token.startsWith("Bearer ")) {
                String jwtToken = token.substring(7);
                Claims claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(jwtToken)
                    .getBody();
                userId = Long.parseLong(claims.getSubject());
            }
            */

            CommentResponseDto updated = commentService.updateComment(logId, commentId, userId, commentRequestDto);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            if (e.getMessage().contains("NOT FOUND")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ErrorResponse.notFound("해당 comment 존재 안함"));
            } else if (e.getMessage().contains("UNAUTHORIZED")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(ErrorResponse.unauthorized("인증 필요"));
            } else if (e.getMessage().contains("FORBIDDEN")) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(ErrorResponse.forbidden("USER ID 다름"));
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ErrorResponse.internalServerError("내부 서버 오류"));
        }
    }

    @DeleteMapping("/{logId}/comments/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable Integer logId,
                                           @PathVariable Integer commentId,
                                           @RequestHeader(value = "Authorization", required = false) String token) {
        try {
            // TODO : get JWT userId
            Integer userId = 1;
            
            // JWT 토큰이 있는 경우 토큰에서 userId 추출
            /*
            if (token != null && token.startsWith("Bearer ")) {
                String jwtToken = token.substring(7);
                Claims claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(jwtToken)
                    .getBody();
                userId = Long.parseLong(claims.getSubject());
            }
            */

            commentService.deleteComment(logId, commentId, userId);
            return ResponseEntity.ok(Map.of("msg", "DELETED"));
        } catch (RuntimeException e) {
            if (e.getMessage().contains("NOT FOUND")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ErrorResponse.notFound("해당 댓글 존재 안함"));
            } else if (e.getMessage().contains("UNAUTHORIZED")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(ErrorResponse.unauthorized("인증 필요"));
            } else if (e.getMessage().contains("FORBIDDEN")) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(ErrorResponse.forbidden("USER ID 다름"));
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ErrorResponse.internalServerError("내부 서버 오류"));
        }
    }

}


