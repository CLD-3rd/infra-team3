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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.time.LocalDate;

@RequiredArgsConstructor
@Service
public class MypageService {

    private final MypageRepository mypageRepository;
    private final UserRepository userRepository;
    private final CountryRepository countryRepository;
    private final PasswordEncoder passwordEncoder;

    public MypageResponseDto getUserProfile(String username) {
        User user = userRepository.findByUserName(username);
        if (user == null) {
            throw new IllegalArgumentException("사용자 정보를 찾을 수 없습니다.");
        }

        List<PlanDto> plans = getMyPlans(username);

        return new MypageResponseDto(plans); // 예시로 plans만 넣음. 생성자에 맞게 수정 필요
    }

    @Transactional
    public void editUser(String username, MypageRequestDto dto) {
        User user = userRepository.findByUserName(username);
        if (user == null) {
            throw new IllegalArgumentException("사용자 정보를 찾을 수 없습니다.");
        }

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

        user.setNickname(dto.getNickname());
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

    public List<PlanDto> getMyPlans(String username) {
        List<WishList> wishlists = mypageRepository.findAllWishlistsByUsername(username);
        List<PlanDto> result = new ArrayList<>();
        for (WishList w : wishlists) {
            // TODO: 필요한 필드 채워서 PlanDto에 넣기
            result.add(new PlanDto(/* 적절한 인자 넣기 */));
        }
        return result;
    }

    @Transactional
    public void addMyPlan(String username, PlanRequestDto dto) {
        User user = userRepository.findByUserName(username);
        if (user == null) throw new IllegalArgumentException("사용자 없음");

        Optional<Country> countryOpt = countryRepository.findAll().stream()
            .filter(c -> c.getName().equals(dto.getCountryName()))
            .findFirst();

        if (countryOpt.isEmpty()) throw new IllegalArgumentException("해당 국가 정보가 없습니다.");
        Country country = countryOpt.get();

        LocalDate travelDate = LocalDate.parse(dto.getTravelDate());
        LocalDate endDate = LocalDate.parse(dto.getEndDate()); // 수정된 부분

        WishList plan = WishList.builder()
            .user(user)
            .country(country)
            .travelDate(travelDate)
            .endDate(endDate)
            .build();

        mypageRepository.save(plan);
    }

    @Transactional
    public void deleteMyPlan(String username, Integer planId) {
        WishList plan = mypageRepository.findById(planId)
                .orElseThrow(() -> new IllegalArgumentException("일정(Plan)이 존재하지 않습니다."));
        if (!plan.getUser().getUserName().equals(username))
            throw new IllegalArgumentException("본인의 일정만 삭제할 수 있습니다.");
        mypageRepository.delete(plan);
    }
}
