package com.Globetrek.service;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.stereotype.Service;

import com.Globetrek.entity.TravelLog;
import com.Globetrek.repository.TravelLogRepository;

@Service
@RequiredArgsConstructor
public class TravelLogService {  

	private final TravelLogRepository travelLogRepository; 

    public List<TravelLog> getTravelLogsByCountryId(Integer countryId) {
        return travelLogRepository.findByCountryId(countryId); 
    }

}
