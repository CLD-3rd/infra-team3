package com.Globetrek.service;

import com.Globetrek.dto.Request.MypageRequestDto;
import com.Globetrek.dto.Response.CommentResponseDto;
import com.Globetrek.dto.Response.MypageResponseDto;
import com.Globetrek.dto.Response.MypageResponseDto.PlanDto;
import com.Globetrek.dto.Response.MypageResponseDto.PlanRequestDto;
import com.Globetrek.entity.Comment;
import com.Globetrek.entity.User;
import com.Globetrek.entity.WishList;
import com.Globetrek.entity.Country;
import com.Globetrek.repository.MypageRepository;
import com.Globetrek.repository.UserRepository;
import com.Globetrek.repository.CountryRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder; // 추가
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MypageService {

    private final MypageRepository mypageRepository;
    private final UserRepository userRepository;
    private final CountryRepository countryRepository;
    private final PasswordEncoder passwordEncoder; // ⭐ 추가

    // ✅ 마이페이지 정보 + 일정(Plan)만 포함 (좋아요 제거)
    public MypageResponseDto getUserProfile(String username) {
        User user = userRepository.findByUserName(username);
        if (user == null) {
            throw new IllegalArgumentException("사용자 정보를 찾을 수 없습니다.");
        }

        List<PlanDto> plans = getMyPlans(username);

        return new MypageResponseDto(
        );
    }

    @Transactional
    public void editUser(String username, MypageRequestDto dto) {
        User user = userRepository.findByUserName(username);

        if (user == null) {
            throw new IllegalArgumentException("사용자 정보를 찾을 수 없습니다.");
        }

        // ⭐⭐⭐ 수정: passwordEncoder로 비밀번호 확인
        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        if (user.getNickname().equals(dto.getNickname())) {
            throw new IllegalArgumentException("기존 닉네임과 동일합니다.");
        }

        List<User> allUsers = userRepository.findAll();
        for (User u : allUsers) {
            if (u.getNickname().equals(dto.getNickname())) {
                throw new IllegalArgumentException("이미 사용 중인 닉네임입니다.");
            }
        }

        user.setNickname(dto.getNickname()); // JPA dirty checking
    }

    public List<CommentResponseDto> getMyComments(String username) {
        List<Comment> comments = mypageRepository.findAllCommentsByUsername(username);
        List<CommentResponseDto> dtos = new ArrayList<>();
        for (Comment c : comments) {
            CommentResponseDto dto = new CommentResponseDto();
            dto.setCommentId(c.getId());
            dto.setUserId(c.getUser().getId());
            dto.setUserNickname(c.getUser().getNickname());
            dto.setContent(c.getContent());
            dto.setCreatedAt(c.getCreatedAt());
            dto.setUpdatedAt(c.getUpdatedAt());
            dtos.add(dto);
        }
        return dtos;
    }

    // ✅ 일정(Plan) 목록 조회 (WishList 기준)
    public List<PlanDto> getMyPlans(String username) {
        List<WishList> wishlists = mypageRepository.findAllWishlistsByUsername(username);
        List<PlanDto> result = new ArrayList<>();
        for (WishList w : wishlists) {
            result.add(new PlanDto(
            ));
        }
        return result;
    }

    // ✅ 일정(Plan) 등록
    @Transactional
    public void addMyPlan(String username, PlanRequestDto dto) {
        User user = userRepository.findByUserName(username);
        if (user == null) throw new IllegalArgumentException("사용자 없음");

        // CountryRepository에 findByName 없음 → findAll로 필터링
        Optional<Country> countryOpt = countryRepository.findAll().stream()
            .filter(c -> c.getName().equals(dto.getCountryName()))
            .findFirst();

        if (countryOpt.isEmpty()) throw new IllegalArgumentException("해당 국가 정보가 없습니다.");
        Country country = countryOpt.get();

        WishList plan = WishList.builder()
            .user(user)
            .country(country)
            .travelDate(dto.getTravelDate())
            .endDate(dto.getEndDate())
            .build();
        mypageRepository.save(plan);
    }

    // ✅ 일정(Plan) 삭제 (본인 것만 삭제 가능)
    @Transactional
    public void deleteMyPlan(String username, Integer planId) {
        WishList plan = mypageRepository.findById(planId)
                .orElseThrow(() -> new IllegalArgumentException("일정(Plan)이 존재하지 않습니다."));
        if (!plan.getUser().getUserName().equals(username))
            throw new IllegalArgumentException("본인의 일정만 삭제할 수 있습니다.");
        mypageRepository.delete(plan);
    }
}