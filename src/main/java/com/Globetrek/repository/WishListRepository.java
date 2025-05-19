package com.Globetrek.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Globetrek.entity.WishList;

@Repository
public interface WishListRepository extends JpaRepository<WishList, Integer> {

}