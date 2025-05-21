package com.Globetrek.controller;

import com.Globetrek.dto.Response.CommentResponseDto;
import com.Globetrek.dto.Response.TravelLogResponseDto;
import com.Globetrek.service.CommentService;
import com.Globetrek.service.TravelLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/gallery")
public class TravelLogController {
    private final TravelLogService travelLogService;
    private final CommentService commentService;

    @GetMapping("/{log_id}")
    public String getSpecificTravelLog(@PathVariable("log_id") Integer log_id,
                                     Model model,
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

            TravelLogResponseDto travelLogResponseDTO = travelLogService.getSpecificTL(log_id);
            List<TravelLogResponseDto> relatedTL = travelLogService.getRelatedTL(log_id);
            List<CommentResponseDto> comments = commentService.getAllComments(log_id);

            model.addAttribute("mainPhoto", travelLogResponseDTO);
            model.addAttribute("comments", comments);
            model.addAttribute("relatedPhotos", relatedTL);
            model.addAttribute("currentUserId", userId);

            return "travelLog";
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
