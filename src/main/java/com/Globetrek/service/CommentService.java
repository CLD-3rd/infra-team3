package com.Globetrek.service;

import com.Globetrek.dto.Request.CommentRequestDto;
import com.Globetrek.dto.Response.CommentResponseDto;
import com.Globetrek.entity.Comment;
import com.Globetrek.entity.User;
import com.Globetrek.repository.CommentRepository;
import com.Globetrek.repository.TravelLogRepository;
import com.Globetrek.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final TravelLogRepository travelLogRepository;
    private final TravelLogService travelLogService;

    private CommentResponseDto converToDto(Comment comment) {
        User user = userRepository.findById(comment.getUser().getId()).orElseThrow(() -> new RuntimeException("USER NOT FOUND"));

        CommentResponseDto dto = new CommentResponseDto();
        dto.setCommentId(comment.getId());
        dto.setUserId(comment.getUser().getId());
        dto.setContent(comment.getContent());
        dto.setCreatedAt(comment.getCreatedAt());
        dto.setUpdatedAt(comment.getUpdatedAt());
        dto.setUserNickname(user.getNickname());

        return dto;
    }

    public List<CommentResponseDto> getAllComments(Integer logId) {
        return commentRepository.findAllByTravelLog_Id(logId).stream()
                .map(this::converToDto)
                .collect(Collectors.toList());
    }

    public CommentResponseDto createComment(Integer logId, Integer userId, CommentRequestDto commentRequestDto) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("USRE NOT FOUND"));

        Comment comment = new Comment();
        comment.setTravelLog(travelLogRepository.findById(logId).orElseThrow(() -> new RuntimeException("TL NOT FOUND")));
        comment.setUser(userRepository.findById(userId).orElseThrow(() -> new RuntimeException("USER NOT FOUND")));
        comment.setContent(commentRequestDto.getContent());

        //Comment count + 1
        travelLogService.updateCommentCount(logId, true);

        return converToDto(commentRepository.save(comment));
    }

    public CommentResponseDto updateComment(Integer logId, Integer commentId, Integer userId, CommentRequestDto commentRequestDto) {
        Comment comment = commentRepository.findByIdAndTravelLog_Id(commentId, logId).orElseThrow(() -> new RuntimeException("COMMENT NOT FOUND"));
        if (!comment.getUser().getId().equals(userId)) {
            throw new RuntimeException("FORBIDDEN");
        }
        if (commentRequestDto.getContent() != null) {
            comment.setContent(commentRequestDto.getContent());
        }
        return converToDto(commentRepository.save(comment));
    }

    public void deleteComment(Integer logId, Integer commentId, Integer userId) {
        Comment comment = commentRepository.findByIdAndTravelLog_Id(commentId, logId).orElseThrow(() -> new RuntimeException("COMMENT NOT FOUND"));
        if (!comment.getUser().getId().equals(userId)) {
            throw new RuntimeException("FORBIDDENT");
        }
        commentRepository.delete(comment);

        //TL comment count -1
        travelLogService.updateCommentCount(logId, false);
    }
}
