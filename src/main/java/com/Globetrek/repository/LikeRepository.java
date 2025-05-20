package com.Globetrek.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Globetrek.entity.Like;
import com.Globetrek.entity.LikeId;

@Repository
public interface LikeRepository extends JpaRepository<Like, LikeId> {

    boolean existsByTravelLog_IdAndUser_Id(Integer travelLogId, Integer userId);

    void deleteByTravelLog_IdAndUser_Id(Integer travelLogId, Integer userId);
}