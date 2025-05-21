package com.Globetrek.repository;

import com.Globetrek.entity.Comment;
import com.Globetrek.entity.User;
import com.Globetrek.entity.WishList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MypageRepository extends JpaRepository<WishList, Integer> {
	
    List<WishList> findAllByUser_UserName(String userName);

    // ✅ 유저 정보 (WishList의 user와 연결, UserRepository로 분리 권장)

    // ✅ 내가 쓴 댓글 리스트
    @Query("SELECT c FROM Comment c WHERE c.user.userName = :username ORDER BY c.createdAt DESC")
    List<Comment> findAllCommentsByUsername(@Param("username") String username);

    // ✅ 일정(Plan, WishList) 리스트 (마이플랜)
    @Query("SELECT w FROM WishList w JOIN FETCH w.country WHERE w.user.userName = :username ORDER BY w.travelDate DESC")
    List<WishList> findAllWishlistsByUsername(@Param("username") String username);

    // ✅ WishList(Plan) 단건 조회 (본인 확인용)
    @Query("SELECT w FROM WishList w WHERE w.id = :planId")
    Optional<WishList> findById(@Param("planId") Integer planId);
}
