package com.Globetrek.repository;

import com.Globetrek.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MypageRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUserName(String userName);
}