package com.project01_teamA.camping_lounge.dto.response;

import com.project01_teamA.camping_lounge.entity.Review;
import com.project01_teamA.camping_lounge.entity.ReviewPopularity;
import com.project01_teamA.camping_lounge.entity.ReviewSurvey;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ResReviewWriteDto {

    private Long reviewId;
    private String reviewTitle;
    private String reviewContent;

    private Long surveyId;
    private Integer surveySatisfaction;
    private Integer surveySiteSize;
    private Integer surveyCleanStatus;
    private Integer surveyKindness;

    private Long memberId;


    @Builder
    public ResReviewWriteDto(Long memberId, Long reviewId, String reviewTitle, String reviewContent, Long surveyId, Integer surveySatisfaction, Integer surveySiteSize, Integer surveyCleanStatus, Integer surveyKindness) {
        this.reviewId = reviewId;
        this.reviewTitle = reviewTitle;
        this.reviewContent = reviewContent;
        this.surveyId = surveyId;
        this.surveySatisfaction = surveySatisfaction;
        this.surveySiteSize = surveySiteSize;
        this.surveyCleanStatus = surveyCleanStatus;
        this.surveyKindness = surveyKindness;
        this.memberId = memberId;
    }

    public static ResReviewWriteDto fromEntity(Review review, ReviewSurvey reviewSurvey){
        return  ResReviewWriteDto.builder()
                .reviewId(review.getReviewId())
                .reviewTitle(review.getReviewTitle())
                .reviewContent(review.getReviewContent())
                .surveyId(reviewSurvey.getSurveyId())
                .surveySatisfaction(reviewSurvey.getSurveySatisfaction())
                .surveySiteSize(reviewSurvey.getSurveySiteSize())
                .surveyCleanStatus(reviewSurvey.getSurveyCleanStatus())
                .surveyKindness(reviewSurvey.getSurveyKindness())
                .memberId(review.getMember().getId())
                .build();
    }
}
