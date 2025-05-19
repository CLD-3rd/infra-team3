package com.Globetrek.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Globetrek.entity.TravelLog;

@Repository
public interface TravelLogRepository extends JpaRepository<TravelLog, Integer> {

}
