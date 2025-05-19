package com.example.demo.dto;

import lombok.Data;

@Data
public class CommentDTO {
    private Integer commentId;
    private String userNickname;
    private String content;
    private Long userId;

    public CommentDTO(Integer commentId, String userNickname, String content, Long userId) {
        this.commentId = commentId;
        this.userNickname = userNickname;
        this.content = content;
        this.userId = userId;
    }
}