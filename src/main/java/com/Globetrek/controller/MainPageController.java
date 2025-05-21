package com.Globetrek.controller;

import lombok.RequiredArgsConstructor;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.Globetrek.entity.Country;
import com.Globetrek.entity.TravelLog;
import com.Globetrek.repository.CountryRepository;
import com.Globetrek.service.TravelLogService;




@Controller
public class MainPageController {

    private final CountryRepository countryRepository;
    private final TravelLogService travelLogService;

    @Autowired
    public MainPageController(CountryRepository countryRepository,
    		TravelLogService travelLogService) {
        this.countryRepository = countryRepository;
        this.travelLogService = travelLogService;
    }

    @GetMapping("/countries")
    public String listCountries(Model model) {
        List<Country> countries = countryRepository.findAll();
        model.addAttribute("countries", countries);
        return "map"; // countries.html
    }

    @GetMapping("/countries/{id}/gallery")
    public String viewGallery(@PathVariable("id") Integer id, Model model) {
        Country country = countryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid country ID: " + id));
        List<TravelLog> travelLogs = travelLogService.getTravelLogsByCountryId(id);
        model.addAttribute("country", country);
        model.addAttribute("travelLogs", travelLogs);
        return "gallery"; // gallery.html
    }
    
    
    //한재선 수정 부분. map.html 검색컨트롤러   
    @GetMapping("/search")
    public String searchCountry(@RequestParam("q") String keyword) {
        return countryRepository.findByNameIgnoreCase(keyword)
            .map(country -> "redirect:/countries/" + country.getId() + "/gallery")
            .orElse("redirect:/countries?error=notfound");
    }  
    //여기까지
    
    
}