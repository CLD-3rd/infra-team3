package com.Globetrek.controller;

import com.Globetrek.dto.Request.WishListRequestDto;
import com.Globetrek.dto.Response.ErrorResponse;
import com.Globetrek.dto.Response.PageInfo;
import com.Globetrek.dto.Response.WLResponse;
import com.Globetrek.dto.Response.WishListReponseDto;
import com.Globetrek.service.WishListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class WishListController {
    private final WishListService wishListService;

    @GetMapping("/{userId}/wishlists")
    public ResponseEntity<?> getAllWishlists(@PathVariable Integer userId,
                                             @RequestParam(defaultValue = "1") int page,
                                             @RequestParam(defaultValue = "10") int size,
                                             @RequestParam(defaultValue = "createdAt") String sort) {
        try {
            // TODO : get JWT userId
            Integer currentUserId = 1;


            if (!currentUserId.equals(userId)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(ErrorResponse.forbidden("USER ID 다름"));
            }

            List<WishListReponseDto> wishlists = wishListService.getAllWishlists(userId, page, size, sort);
            PageInfo pageInfo = wishListService.getPageInfo(userId, page, size);

            return ResponseEntity.ok(WLResponse.success(wishlists, pageInfo));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ErrorResponse.internalServerError("내부 서버 오류"));
        }
    }

    @GetMapping("/{userId}/wishlists/{wishlistId}")
    public ResponseEntity<?> getWishlist(@PathVariable Integer userId,
                                         @PathVariable Integer wishlistId) {
        try {
            // TODO : get JWT userId
            Integer currentUserId = 1;


            if (!currentUserId.equals(userId)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(ErrorResponse.forbidden("USER ID 다름"));
            }

            WishListReponseDto wishlist = wishListService.getWishlist(userId, wishlistId);
            return ResponseEntity.ok(WLResponse.success(wishlist));
        } catch (RuntimeException e) {
            if (e.getMessage().contains("USER NOT FOUND")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ErrorResponse.notFound("해당 USER 존재 안함"));
            } else if (e.getMessage().contains("COUNTRY NOT FOUND")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ErrorResponse.notFound("해당 COUNTRY 존재 안함"));
            } else if (e.getMessage().contains("WL EXISTS")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ErrorResponse.notFound("해당 WL EXISTS"));
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ErrorResponse.internalServerError("내부 서버 오류"));
        }
    }

    @PostMapping("/{userId}/wishlists")
    public ResponseEntity<?> createWishlist(@PathVariable Integer userId,
                                            @RequestBody WishListRequestDto wishListRequestDto) {
        try {
            // TODO : get JWT userId
            Integer currentUserId = 1;

            if (!currentUserId.equals(userId)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(ErrorResponse.forbidden("USER ID 다름"));
            }

            WishListReponseDto createdWishlist = wishListService.createWishlist(userId, wishListRequestDto);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(WLResponse.success(createdWishlist));
        } catch (RuntimeException e) {
            if (e.getMessage().contains("not found")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ErrorResponse.notFound("해당 국가 존재 안함"));
            } else if (e.getMessage().contains("WL EXISTS")) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(ErrorResponse.conflict("이미 동일한 날짜로 등록된 위시리스트가 있습니다."));
            } else if (e.getMessage().contains("date")) {
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                        .body(ErrorResponse.unprocessableEntity("travel date > end date"));
            } else if (e.getMessage().contains("COUNTRY NOT FOUND")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ErrorResponse.notFound("해당 COUNTRY 존재 안함"));
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ErrorResponse.internalServerError("내부 서버 오류"));
        }
    }

    @PutMapping("/{userId}/wishlists/{wishlistId}")
    public ResponseEntity<?> updateWishlist(@PathVariable Integer userId,
                                            @PathVariable Integer wishlistId,
                                            @RequestBody WishListRequestDto wishListRequestDto) {
        try {
            // TODO : get JWT userId
            Integer currentUserId = 1;


            if (!currentUserId.equals(userId)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(ErrorResponse.forbidden("USER ID 다름"));
            }

            WishListReponseDto updatedWishlist = wishListService.updateWishlist(userId, wishlistId, wishListRequestDto);
            return ResponseEntity.ok(WLResponse.success(updatedWishlist));
        } catch (RuntimeException e) {
            if (e.getMessage().contains("not found")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ErrorResponse.notFound("해당 wishlist 존재 안함"));
            } else if (e.getMessage().contains("duplicate")) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(ErrorResponse.conflict("중복 wishlist"));
            } else if (e.getMessage().contains("date")) {
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                        .body(ErrorResponse.unprocessableEntity("travel date > end date"));
            } else if (e.getMessage().contains("COUNTRY NOT FOUND")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ErrorResponse.notFound("해당 COUNTRY 존재 안함"));
            } else if (e.getMessage().contains("WL EXISTS")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ErrorResponse.notFound("해당 WL EXISTS"));
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ErrorResponse.internalServerError("내부 서버 오류"));
        }
    }

    @DeleteMapping("/{userId}/wishlists/{wishlistId}")
    public ResponseEntity<?> deleteWishlist(@PathVariable Integer userId,
                                            @PathVariable Integer wishlistId) {
        try {
            // TODO : get JWT userId
            Integer currentUserId = 1;


            if (!currentUserId.equals(userId)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(ErrorResponse.forbidden("USER ID 다름"));
            }

            wishListService.deleteWishlist(userId, wishlistId);
            return ResponseEntity.ok(Map.of("msg", "DELETED"));
        } catch (RuntimeException e) {
            if (e.getMessage().contains("not found")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ErrorResponse.notFound("해당 wishlist 존재 안함"));
            } else if (e.getMessage().contains("COUNTRY NOT FOUND")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ErrorResponse.notFound("해당 COUNTRY 존재 안함"));
            } else if (e.getMessage().contains("WL EXISTS")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ErrorResponse.notFound("해당 WL EXISTS"));
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ErrorResponse.internalServerError("내부 서버 오류"));
        }
    }

}
