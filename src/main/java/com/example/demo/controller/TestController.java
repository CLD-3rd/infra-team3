package com.example.demo.controller;

import com.example.demo.dto.CommentDTO;
import com.example.demo.dto.RelatedPhotoDTO;
import com.example.demo.dto.TravelLogDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class TestController {

    @GetMapping("/travel-log")
    public String showTravelLogPage(Model model) {
        TravelLogDTO dummy = new TravelLogDTO();
        dummy.setPicUrl("/images/test.png");
        dummy.setTitle("TITLE");
        dummy.setLikeCount(123);
        dummy.setCreatedAt(LocalDateTime.now());
        dummy.setLogId(1L);
        dummy.setLiked(true);
        dummy.setWished(true);
        dummy.setCountryId(1L);
        dummy.setCountryName("Russia");
        dummy.setLiked(true);
        dummy.setWished(false);

        // comments
        List<CommentDTO> comments = List.of(
                new CommentDTO(1, "user1", "comment1", 1L),
                new CommentDTO(2, "user2", "comment2", 2L)
        );

        // relatedPhotos
        List<RelatedPhotoDTO> related = List.of(
                new RelatedPhotoDTO(1L, "/images/test.png"),
                new RelatedPhotoDTO(2L, "/images/test.png")
        );

        // current user
        model.addAttribute("currentUserId", 2L);
        model.addAttribute("comments", comments);
        model.addAttribute("relatedPhotos", related);
        model.addAttribute("mainPhoto", dummy);
        return "travelLog";
    }
}