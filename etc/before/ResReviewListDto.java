package com.project01_teamA.camping_lounge.dto.response;

import com.project01_teamA.camping_lounge.entity.Review;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class ResReviewListDto {
    private Long reviewId;
    private String reviewTitle;
    private String reviewContent;
    private String reviewPostingDate;
    private List<ResReviewFileUploadDto> reviewImages;
    private int reviewHit;

    @Builder
    public ResReviewListDto(Long reviewId, String reviewTitle, String reviewContent, String reviewPostingDate, List<ResReviewFileUploadDto> reviewImages, int reviewHit) {
        this.reviewId = reviewId;
        this.reviewTitle = reviewTitle;
        this.reviewContent = reviewContent;
        this.reviewPostingDate = reviewPostingDate;
        this.reviewImages = reviewImages;
        this.reviewHit = reviewHit;
    }

    public static ResReviewListDto fromEntity (Review review){
        return ResReviewListDto.builder()
                .reviewId(review.getReviewId())
                .reviewTitle(review.getReviewTitle())
                .reviewContent(review.getReviewContent())
                .reviewPostingDate(review.getReviewPostingDate())
                .reviewImages(review.getReviewImages().stream()
                        .map(ResReviewFileUploadDto::fromEntity)
                        .collect(Collectors.toList()))
                .reviewHit(review.getReviewPopularity().getReviewHit())
                .build();
    }
}
