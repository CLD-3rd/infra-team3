package com.Globetrek.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Globetrek.entity.Country;

@Repository
public interface CountryRepository extends JpaRepository<Country, Integer> {
	//한재선 수정부분
	Optional<Country> findByNameIgnoreCase(String name);
	//여기까지
}