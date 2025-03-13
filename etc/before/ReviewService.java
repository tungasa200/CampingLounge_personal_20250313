package com.project01_teamA.camping_lounge.service;

import com.project01_teamA.camping_lounge.dto.request.ReviewUpdateDto;
import com.project01_teamA.camping_lounge.dto.request.ReviewWriteDto;
import com.project01_teamA.camping_lounge.dto.response.*;
import com.project01_teamA.camping_lounge.entity.*;
import com.project01_teamA.camping_lounge.entity.camp.Campsite;
import com.project01_teamA.camping_lounge.entity.reservation.Reservation;
import com.project01_teamA.camping_lounge.exception.ResourceNotFoundException;
import com.project01_teamA.camping_lounge.repository.*;
import com.project01_teamA.camping_lounge.repository.camp.CampRepository;
import com.project01_teamA.camping_lounge.repository.reservation.ReservationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReviewPopularityRepository reviewPopularityRepository;
    private final ReviewSurveyRepository reviewSurveyRepository;
    private final ReviewFileRepository reviewFileRepository;
    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final ReservationRepository reservationRepository;
    private final CampRepository campRepository;

    // Review List
    public Page<ResReviewListDto> getAllReviews(Pageable pageable){
        Page<Review> page = reviewRepository.findAllWithPopularity(pageable);
        List<ResReviewListDto> list = page.stream().map(ResReviewListDto::fromEntity).toList();
        return new PageImpl<>(list, pageable, page.getTotalElements());
    }

    // Review Write
    public ResReviewWriteDto writeReviews(ReviewWriteDto reviewWriteDto) {

        // 1. 예약 ID와 멤버 ID가 유효한지 확인
        if (reviewWriteDto.getReservationId() == null) {
            throw new IllegalArgumentException("예약 ID가 null입니다.");
        }
        if (reviewWriteDto.getMemberId() == null) {
            throw new IllegalArgumentException("회원 ID가 null입니다.");
        }

        // 2. 멤버 ID를 이용해 멤버 정보 조회
        Member member = memberRepository.findById(reviewWriteDto.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));

        // 3. 예약 정보 조회
        Reservation reservation = reservationRepository.findById(reviewWriteDto.getReservationId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 예약입니다."));

        // 4. 예약에 대한 리뷰가 이미 존재하는지 확인
        if (reservation.getId() != null && reservationRepository.existsById(reservation.getId())) {
            throw new IllegalStateException("이 예약에 대한 리뷰는 이미 작성되었습니다.");
        }

        // 5. 리뷰 제목이 비어있지 않도록 확인
        if (reviewWriteDto.getReviewTitle() == null || reviewWriteDto.getReviewTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("리뷰 제목을 입력해주세요.");
        }

        // 6. 설문 항목이 모두 채워졌는지 확인
        if (reviewWriteDto.getSurveySatisfaction() == null ||
                reviewWriteDto.getSurveySiteSize() == null ||
                reviewWriteDto.getSurveyCleanStatus() == null ||
                reviewWriteDto.getSurveyKindness() == null) {
            throw new IllegalArgumentException("모든 설문 항목을 입력해주세요.");
        }

        // 7. Review 엔티티 생성 및 Member, Reservation 연결
        Review review = reviewWriteDto.toEntity(member);  // member를 전달하여 Review 생성

        // 8. Review 저장
        Review savedReview = reviewRepository.save(review);

        // 9. ReviewSurvey 엔티티 생성
        ReviewSurvey reviewSurvey = ReviewSurvey.builder()
                .review(savedReview)
                .surveySatisfaction(reviewWriteDto.getSurveySatisfaction())
                .surveySiteSize(reviewWriteDto.getSurveySiteSize())
                .surveyCleanStatus(reviewWriteDto.getSurveyCleanStatus())
                .surveyKindness(reviewWriteDto.getSurveyKindness())
                .build();

        // 10. ReviewSurvey 저장
        ReviewSurvey savedReviewSurvey = reviewSurveyRepository.save(reviewSurvey);
        savedReview.setReviewSurvey(savedReviewSurvey);

        // 11. DTO 반환
        return ResReviewWriteDto.fromEntity(savedReview, reviewSurvey);
    }

    // Review Detail
    public ResReviewDetailDto detailReviews(Long reviewId, boolean incrementLike){
        // 리뷰 조회
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("해당 리뷰가 존재하지 않습니다. ID: " + reviewId));

        // 리뷰와 연관된 ReviewPopularity가 없으면 새로 생성하고 저장
        if (review.getReviewPopularity() == null) {
            ReviewPopularity reviewPopularity = ReviewPopularity.builder()
                    .reviewHit(0)  // 초기값 설정
                    .reviewLikes(0)  // 초기값 설정
                    .review(review)  // Review 객체 연결
                    .build();
            review.setReviewPopularity(reviewPopularity);
            reviewPopularityRepository.save(reviewPopularity);  // DB에 저장
        }

        // 조회수 증가
        review.getReviewPopularity().incrementReviewHit();
        reviewPopularityRepository.save(review.getReviewPopularity()); // 증가된 조회수를 DB에 반영

        List<Comment> comment = review.getComment();



        return ResReviewDetailDto.fromEntity(review);
    }
    // ReviewSurvey 데이터를 가져오는 메서드 추가
    public ResReviewSurveyDto getReviewSurvey(Long reviewId) {
        // ReviewSurvey 조회
        ReviewSurvey reviewSurvey = reviewSurveyRepository.findByReview_ReviewId(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("해당 리뷰에 대한 설문이 존재하지 않습니다. ReviewId: " + reviewId));

        // DTO로 변환하여 반환
        return ResReviewSurveyDto.fromEntity(reviewSurvey);
    }


    // Review Delete
    public void deleteReviews(Long reviewId){
        // 관련된 파일 삭제
        reviewSurveyRepository.deleteById(reviewId);
        commentRepository.deleteById(reviewId);
        reviewPopularityRepository.deleteById(reviewId);
        reviewFileRepository.deleteById(reviewId);
        reviewRepository.deleteById(reviewId);
    }

    // Review Update
    public ResReviewDetailDto updateReviews(Long reviewId, ReviewUpdateDto reviewUpdateDto){
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Review", "Review Id", String.valueOf(reviewId)));
        review.update(reviewUpdateDto.getReviewTitle(), reviewUpdateDto.getReviewContent());

        // ReviewSurvey 수정 (설문 항목들)
        ReviewSurvey reviewSurvey = review.getReviewSurvey();
        if (reviewSurvey != null) {
            // 기존 설문이 있으면, 수정된 설문 값으로 업데이트
            reviewSurvey.update(
                    reviewUpdateDto.getSurveySatisfaction(),
                    reviewUpdateDto.getSurveySiteSize(),
                    reviewUpdateDto.getSurveyCleanStatus(),
                    reviewUpdateDto.getSurveyKindness()
            );
        } else {
            // 만약 ReviewSurvey가 없으면 새로 생성
            reviewSurvey = ReviewSurvey.builder()
                    .review(review)  // Review와 연결
                    .surveySatisfaction(reviewUpdateDto.getSurveySatisfaction())
                    .surveySiteSize(reviewUpdateDto.getSurveySiteSize())
                    .surveyCleanStatus(reviewUpdateDto.getSurveyCleanStatus())
                    .surveyKindness(reviewUpdateDto.getSurveyKindness())
                    .build();
            review.setReviewSurvey(reviewSurvey);
        }

        // Review와 ReviewSurvey 저장
        reviewRepository.save(review);
        reviewSurveyRepository.save(reviewSurvey);
        Review updatedReview = reviewRepository.save(review);

        return ResReviewDetailDto.fromEntity(updatedReview);
    }

    public Page<ResReviewListDto> getBestReviews(Pageable pageable) {
        Page<Review> page = reviewRepository.findBestReviews(pageable);
        List<ResReviewListDto> list = page.stream().map(ResReviewListDto::fromEntity).collect(Collectors.toList());
        return new PageImpl<>(list, pageable, page.getTotalElements());
    }
}
