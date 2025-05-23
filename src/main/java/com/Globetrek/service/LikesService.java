package com.Globetrek.service;

import com.Globetrek.dto.Response.LikeResponseDto;
import com.Globetrek.entity.Like;
import com.Globetrek.entity.LikeId;
import com.Globetrek.entity.TravelLog;
import com.Globetrek.entity.User;
import com.Globetrek.repository.LikeRepository;
import com.Globetrek.repository.TravelLogRepository;
import com.Globetrek.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class LikesService {
    private final LikeRepository likeRepository;
    private final TravelLogRepository travelLogRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public boolean isLiked(Integer userId, Integer logId) {
        return likeRepository.existsByTravelLog_IdAndUser_Id(logId, userId);
    }

    public LikeResponseDto toggleLike(Integer logId, Integer userId) {
        TravelLog travelLog = travelLogRepository.findById(logId).orElseThrow(() -> new RuntimeException("NOT FOUND"));
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("USER NOT FOUND"));

        boolean isLiked = isLiked(userId, logId);

        if (isLiked) {
            likeRepository.deleteByTravelLog_IdAndUser_Id(logId, userId);
            travelLog.setLikeCount(Math.max(0, travelLog.getLikeCount() - 1));
        } else {
            LikeId likeId = new LikeId(userId, logId);
            Like like = Like.builder()
                    .id(likeId)
                    .travelLog(travelLog)
                    .user(user)
                    .build();
            likeRepository.save(like);
            travelLog.setLikeCount(travelLog.getLikeCount() + 1);
        }
        travelLogRepository.save(travelLog);

        LikeResponseDto response = new LikeResponseDto();
        response.setLiked(!isLiked);
        response.setLikeCount(travelLog.getLikeCount());
        return response;
    }

    public LikeResponseDto getLikeStatus(Integer logId, Integer userId) {
        TravelLog travelLog = travelLogRepository.findById(logId).orElseThrow(() -> new RuntimeException("NOT FOUND"));
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("USER NOT FOUND"));

        LikeResponseDto response = new LikeResponseDto();
        response.setLiked(isLiked(userId, logId));
        response.setLikeCount(travelLog.getLikeCount());
        return response;
    }
}
