package com.Globetrek.controller;

import com.Globetrek.dto.Request.MypageRequestDto;
import com.Globetrek.dto.Response.CommentResponseDto;
import com.Globetrek.dto.Response.CountriesResponseDto;
import com.Globetrek.dto.Response.MypageResponseDto;
import com.Globetrek.service.MypageService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mypage")
public class MypageController {

    private final MypageService mypageService;

    // 세션/시큐리티에서 username 추출
    private String resolveUsername(HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.isAuthenticated()) {
                Object principal = authentication.getPrincipal();
                if (principal instanceof UserDetails userDetails) {
                    username = userDetails.getUsername();
                    session.setAttribute("username", username);
                }
            }
        }
        return username;
    }

    @GetMapping("/view")
    public ModelAndView showPage(HttpSession session) {
        //
        return new ModelAndView("mypage");
    }

    @GetMapping
    public ResponseEntity<?> getMypageInfo(HttpSession session) {
        String username = resolveUsername(session);
        System.out.println("username from session/security: " + username);

        if (username == null) {
            return ResponseEntity.status(401).body(Map.of("message", "로그인이 필요합니다."));
        }
        MypageResponseDto user = mypageService.getUserProfile(username);
        return ResponseEntity.ok(user);
    }

    @PatchMapping("/edit")
    public ResponseEntity<?> updateUser(@RequestBody MypageRequestDto dto, HttpSession session) {
        String username = resolveUsername(session);
        if (username == null) {
            return ResponseEntity.status(401).body(Map.of("message", "로그인이 필요합니다."));
        }
        try {
            mypageService.editUser(username, dto);
            return ResponseEntity.ok(Map.of("message", "수정 성공"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    @GetMapping("/comments")
    public ResponseEntity<?> getMyComments(HttpSession session) {
        String username = resolveUsername(session);
        if (username == null) {
            return ResponseEntity.status(401).body(Map.of("message", "로그인이 필요합니다."));
        }
        List<CommentResponseDto> result = mypageService.getMyComments(username);
        return ResponseEntity.ok(result);
    }

//@GetMapping("/plan")
//public ResponseEntity<?> getMyPlans(HttpSession session) {
//	String username = resolveUsername(session);
//	if (username == null) {
//		return ResponseEntity.status(401).body(Map.of("message", "로그인이 필요합니다."));
//	}
//	List<MypageResponseDto.PlanDto> plans = mypageService.getMyPlans(username);
//	return ResponseEntity.ok(plans);
//}

    @PostMapping("/plan")
    public ResponseEntity<?> addMyPlan(@RequestBody MypageResponseDto.PlanRequestDto dto, HttpSession session) {
        String username = resolveUsername(session);
        if (username == null) {
            return ResponseEntity.status(401).body(Map.of("message", "로그인이 필요합니다."));
        }
        try {
            mypageService.addMyPlan(username, dto);
            return ResponseEntity.ok(Map.of("message", "일정 등록 완료!"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    @DeleteMapping("/plan/{planId}")
    public ResponseEntity<?> deleteMyPlan(@PathVariable Integer planId, HttpSession session) {
        String username = resolveUsername(session);
        if (username == null) {
            return ResponseEntity.status(401).body(Map.of("message", "로그인이 필요합니다."));
        }
        try {
            mypageService.deleteMyPlan(username, planId);
            return ResponseEntity.ok(Map.of("message", "삭제 성공"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    @GetMapping("/countries")
    public ResponseEntity<?> getCountries() {
        List<CountriesResponseDto> list = mypageService.getCountries();
        return ResponseEntity.ok().body(list);
    }
}
	

