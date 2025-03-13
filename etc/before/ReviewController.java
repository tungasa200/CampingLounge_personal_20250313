package com.project01_teamA.camping_lounge.controller;

import com.project01_teamA.camping_lounge.dto.request.ReviewUpdateDto;
import com.project01_teamA.camping_lounge.dto.request.ReviewWriteDto;
import com.project01_teamA.camping_lounge.dto.response.ResReviewDetailDto;
import com.project01_teamA.camping_lounge.dto.response.ResReviewListDto;
import com.project01_teamA.camping_lounge.dto.response.ResReviewSurveyDto;
import com.project01_teamA.camping_lounge.dto.response.ResReviewWriteDto;
import com.project01_teamA.camping_lounge.entity.ReviewSurvey;
import com.project01_teamA.camping_lounge.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/review")
@Slf4j
public class ReviewController {

    private final ReviewService reviewService;

    // Review List
    @GetMapping("/list")
    public ResponseEntity<Page<ResReviewListDto>> reviewList(
            @PageableDefault(size = 9, sort = "reviewId", direction = Sort.Direction.DESC)Pageable pageable){
        Page<ResReviewListDto> reviewListDtos = reviewService.getAllReviews(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(reviewListDtos);
    }

    // Review Write (Post)
    @PostMapping("/post")
    public ResponseEntity<ResReviewWriteDto> reviewWrite(@RequestBody ReviewWriteDto reviewWriteDto) {
        log.info("리뷰 작성 요청: {}", reviewWriteDto);
        ResReviewWriteDto savedReviewDto = reviewService.writeReviews(reviewWriteDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedReviewDto);
    }

    // Review Detail
    @GetMapping("/{reviewId}")
    public ResponseEntity<ResReviewDetailDto> reviewDetail(@PathVariable("reviewId") Long reviewId, @RequestParam(defaultValue = "false") boolean incrementLike){
        ResReviewDetailDto resReviewDetailDto = reviewService.detailReviews(reviewId, incrementLike);
        return ResponseEntity.ok(resReviewDetailDto);
    }

    // Review Update
    @PostMapping("/{reviewId}/update")
    public ResponseEntity<ResReviewDetailDto> reviewUpdate(@PathVariable Long reviewId,
                                                           @RequestBody ReviewUpdateDto reviewUpdateDto){
        ResReviewDetailDto resReviewDetailDto = reviewService.updateReviews(reviewId, reviewUpdateDto);
        return ResponseEntity.status(HttpStatus.OK).body(resReviewDetailDto);
    }


    // Review Delete
    @DeleteMapping("/{reviewId}/delete")
    public ResponseEntity<Long> delete(@PathVariable Long reviewId){
        reviewService.deleteReviews(reviewId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    // Survey List
    @GetMapping("/{reviewId}/survey")
    public ResponseEntity<ResReviewSurveyDto> getReviewSurvey(@PathVariable("reviewId") Long reviewId) {
        ResReviewSurveyDto resReviewSurveyDto = reviewService.getReviewSurvey(reviewId);
        return ResponseEntity.ok(resReviewSurveyDto);
    }

    // 베스트 리뷰 조회 (조회수가 100 이상인 리뷰만)
    @GetMapping("/best")
    public ResponseEntity<Page<ResReviewListDto>> getBestReviews(
            @RequestParam("page") int page,
            @RequestParam("size") int size) {

        Pageable pageable = PageRequest.of(page, size);  // Pageable 객체 생성
        Page<ResReviewListDto> bestReviews = reviewService.getBestReviews(pageable);
        return ResponseEntity.ok(bestReviews);
    }

}
