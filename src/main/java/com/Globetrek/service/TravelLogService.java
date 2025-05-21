package com.Globetrek.service;

import com.Globetrek.dto.Response.TravelLogResponseDto;
import com.Globetrek.entity.TravelLog;
import com.Globetrek.repository.TravelLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class TravelLogService {
    private final TravelLogRepository travelLogRepository;
    private final LikesService likesService;
    private final WishListService wishListService;

    public List<TravelLog> getTravelLogsByCountryId(Integer countryId) {
        return travelLogRepository.findByCountryId(countryId);
    }

    public TravelLogResponseDto getSpecificTL(Integer logId) throws Exception {
        TravelLog travelLog = travelLogRepository.findById(logId).orElseThrow(() -> new Exception("TL NOT FOUND"));

        // hit count + 1
        travelLog.setHit(travelLog.getHit() + 1);
        travelLogRepository.save(travelLog);

        // TODO : get JWT userId
        Integer userId = 1;

        TravelLogResponseDto responseDto = TravelLogResponseDto.builder()
                .logId(travelLog.getId())
                .title(travelLog.getTitle())
                .picUrl("/images/countries/" + travelLog.getCountry().getName() + "/" + travelLog.getId() + ".jpg")
                .hit(travelLog.getHit())
                .likeCount(travelLog.getLikeCount())
                .commentCount(travelLog.getCommentCount())
                .countryId(travelLog.getCountry().getId())
                .countryName(travelLog.getCountry().getName())
                .createdAt(travelLog.getCreatedAt())
                .updatedAt(travelLog.getUpdatedAt())
                .liked(likesService.isLiked(userId, logId))
                .isWished(wishListService.isWished(userId,travelLog.getCountry().getId()))
                .build();
        return responseDto;
    }

    public void updateCommentCount(Integer logId, boolean increment) {
        TravelLog travelLog = travelLogRepository.findById(logId).orElseThrow(() -> new RuntimeException("TL NOT FOUND"));

        int currentCount = travelLog.getCommentCount();
        travelLog.setCommentCount(increment ? currentCount + 1 : Math.max(0, currentCount - 1));
        travelLogRepository.save(travelLog);
    }

    public List<TravelLogResponseDto> getRelatedTL(Integer logId) throws Exception {

        TravelLog currentTL = travelLogRepository.findById(logId).orElseThrow(() -> new Exception("TL NOT FOUND"));

        String countryName = currentTL.getCountry().getName();
        List<TravelLog> relatedTL = travelLogRepository.findAllByCountry_Name(countryName);

        // TODO : get JWT userId
        Integer userId = 1;

        return relatedTL.stream()
                .filter(tl -> !tl.getId().equals(logId))
                .limit(4) // Return max 4 posts
                .map(tl -> TravelLogResponseDto.builder()
                        .logId(tl.getId())
                        .title(tl.getTitle())
                        .picUrl("/images/countries/" + tl.getCountry().getName() + "/" + tl.getId() + ".jpg")
                        .hit(tl.getHit())
                        .likeCount(tl.getLikeCount())
                        .commentCount(tl.getCommentCount())
                        .countryId(tl.getCountry().getId())
                        .countryName(tl.getCountry().getName())
                        .createdAt(tl.getCreatedAt())
                        .updatedAt(tl.getUpdatedAt())
                        .liked(likesService.isLiked(userId, tl.getId()))
                        .build())
                .collect(Collectors.toList());
    }
}
