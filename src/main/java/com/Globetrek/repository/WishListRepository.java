package com.Globetrek.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Globetrek.entity.WishList;

import java.util.List;
import java.util.Optional;

@Repository
public interface WishListRepository extends JpaRepository<WishList, Integer> {

    boolean existsByUser_IdAndCountry_Id(Integer userId, Integer countryId);

    Optional<WishList> findByIdAndUser_Id(Integer id, Integer userId);

    Page<WishList> findByUser_Id(Integer userId, Pageable pageable);

    List<WishList> findByUser_IdAndCountry_Id(Integer userId, Integer countryId);
}