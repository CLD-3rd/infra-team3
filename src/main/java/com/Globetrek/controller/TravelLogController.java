package com.Globetrek.controller;

import com.Globetrek.dto.Response.CommentResponseDto;
import com.Globetrek.dto.Response.TravelLogResponseDto;
import com.Globetrek.dto.security.LoginDetails;
import com.Globetrek.service.CommentService;
import com.Globetrek.service.TravelLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
                                     @AuthenticationPrincipal LoginDetails loginDetails) {
        try {
            Integer userId = loginDetails != null ? loginDetails.getUser().getId() : null;

            TravelLogResponseDto travelLogResponseDTO = travelLogService.getSpecificTL(log_id, userId);
            List<TravelLogResponseDto> relatedTL = travelLogService.getRelatedTL(log_id, userId);
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
