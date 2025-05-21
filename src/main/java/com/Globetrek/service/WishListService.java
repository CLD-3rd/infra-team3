package com.Globetrek.service;

import com.Globetrek.dto.Request.WishListRequestDto;
import com.Globetrek.dto.Response.PageInfo;
import com.Globetrek.dto.Response.WishListReponseDto;
import com.Globetrek.entity.Country;
import com.Globetrek.entity.User;
import com.Globetrek.entity.WishList;
import com.Globetrek.repository.CountryRepository;
import com.Globetrek.repository.UserRepository;
import com.Globetrek.repository.WishListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class WishListService {  // 예: UserService
    private final WishListRepository wishlistRepository;
    private final UserRepository userRepository;
    private final CountryRepository countryRepository;

    private WishListReponseDto convertToDTO(WishList wishlist) {
        WishListReponseDto dto = new WishListReponseDto();
        dto.setWishlistId(wishlist.getId());
        dto.setCountryId(wishlist.getCountry().getId());
        dto.setTravelDate(wishlist.getTravelDate());
        dto.setEndDate(wishlist.getEndDate());
        dto.setCreatedAt(wishlist.getCreatedAt());
        dto.setUpdatedAt(wishlist.getUpdatedAt());

        // Add country things
        Country country = countryRepository.findById(wishlist.getCountry().getId())
                .orElseThrow(() -> new RuntimeException("COUNTRY NOT FOUND"));
        dto.setCountryName(country.getName());
        dto.setFlagUrl("/images/countries/" + country.getName() + "/flag.png");

        return dto;
    }

    @Transactional(readOnly = true)
    public boolean isWished(Integer userId, Integer countryId) {
        return wishlistRepository.existsByUser_IdAndCountry_Id(userId, countryId);
    }

    public PageInfo getPageInfo(Integer userId, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        Page<WishList> wishlists = wishlistRepository.findByUser_Id(userId, pageRequest);

        return PageInfo.builder()
                .page(page)
                .size(size)
                .totalItems(wishlists.getTotalElements())
                .totalPages(wishlists.getTotalPages())
                .build();
    }

    public List<WishListReponseDto> getAllWishlists(Integer userId, int page, int size, String sort) {
        PageRequest pageRequest = PageRequest.of(page - 1, size, Sort.by(sort));
        Page<WishList> wishlists = wishlistRepository.findByUser_Id(userId, pageRequest);
        return wishlists.getContent().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public WishListReponseDto getWishlist(Integer userId, Integer wishlistId) {
        WishList wishList = wishlistRepository.findByIdAndUser_Id(wishlistId, userId).orElseThrow(() -> new RuntimeException("WL NOT FOUND"));
        return convertToDTO(wishList);
    }

    public WishListReponseDto createWishlist(Integer userId, WishListRequestDto wishListRequestDto) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("USER NOT FOUND"));
        Country country = countryRepository.findById(wishListRequestDto.getCountryId()).orElseThrow(() -> new RuntimeException("COUNTRY NOT FOUDN"));

        // chk if WL w/ same country & EXACT SAME DATES exists
        if (wishListRequestDto.getTravelDate() != null && wishListRequestDto.getEndDate() != null) {
            List<WishList> existingWL = wishlistRepository.findByUser_IdAndCountry_Id(userId, wishListRequestDto.getCountryId());
            for (WishList wl : existingWL) {
                if (wl.getTravelDate() != null && wl.getEndDate() != null) {
                    if (wishListRequestDto.getTravelDate().equals(wl.getTravelDate())
                            && wishListRequestDto.getEndDate().equals(wl.getEndDate())) {
                        throw new RuntimeException("WL EXISTS");
                    }
                }
            }
        }

        WishList wishList = new WishList();
        wishList.setUser(user);
        wishList.setCountry(country);
        wishList.setTravelDate(wishListRequestDto.getTravelDate());
        wishList.setEndDate(wishListRequestDto.getEndDate());
        return convertToDTO(wishlistRepository.save(wishList));
    }

    public WishListReponseDto updateWishlist(Integer userId, Integer wishlistId, WishListRequestDto wishListRequestDto) {
        WishList wishList = wishlistRepository.findByIdAndUser_Id(wishlistId, userId).orElseThrow(() -> new RuntimeException("WL NOT FOUND"));

        if (wishListRequestDto.getCountryId() != null) {
            Country country = countryRepository.findById(wishListRequestDto.getCountryId()).orElseThrow(() -> new RuntimeException("COUNTRY NOT FOUND"));

            wishList.setCountry(country);
        }
        if (wishListRequestDto.getTravelDate() != null) {
            wishList.setTravelDate(wishListRequestDto.getTravelDate());
        }
        if (wishListRequestDto.getEndDate() != null) {
            wishList.setEndDate(wishListRequestDto.getEndDate());
        }

        return convertToDTO(wishlistRepository.save(wishList));
    }

    public void deleteWishlist(Integer userId, Integer wishlistId) {
        WishList wishlist = wishlistRepository.findByIdAndUser_Id(wishlistId, userId).orElseThrow(() -> new RuntimeException("WL NOT FOUND"));
        wishlistRepository.delete(wishlist);
    }




}
