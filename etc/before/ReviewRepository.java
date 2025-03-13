package com.project01_teamA.camping_lounge.repository;

import com.project01_teamA.camping_lounge.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    // Review와 ReviewPopularity를 함께 조회하는 쿼리
    @Query("SELECT r FROM Review r LEFT JOIN FETCH r.reviewPopularity")
    Page<Review> findAllWithPopularity(Pageable pageable);

    // 조회수 높은 순으로 리뷰를 페이지 단위로 조회
    @Query("SELECT r FROM Review r LEFT JOIN FETCH r.reviewPopularity rp WHERE rp.reviewHit >= 20 ORDER BY rp.reviewHit DESC")
    Page<Review> findBestReviews(Pageable pageable);
}
