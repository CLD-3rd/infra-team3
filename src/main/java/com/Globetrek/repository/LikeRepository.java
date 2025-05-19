package com.Globetrek.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Globetrek.entity.Like;
import com.Globetrek.entity.LikeId;

@Repository
public interface LikeRepository extends JpaRepository<Like, LikeId> {

}