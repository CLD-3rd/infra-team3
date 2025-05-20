package com.Globetrek.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Globetrek.entity.Comment;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

    Optional<Comment> findByIdAndTravelLog_Id(Integer id, Integer travelLogId);
    List<Comment> findAllByTravelLog_Id(Integer travelLogId);
}