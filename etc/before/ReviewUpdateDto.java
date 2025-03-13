package com.project01_teamA.camping_lounge.dto.request;

import com.project01_teamA.camping_lounge.entity.Review;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReviewUpdateDto {
    private String reviewTitle;
    private String reviewContent;
    private Integer surveySatisfaction;
    private Integer surveySiteSize;
    private Integer surveyCleanStatus;
    private Integer surveyKindness;

    @Builder
    public ReviewUpdateDto(String reviewTitle, String reviewContent, Integer surveySatisfaction, Integer surveySiteSize, Integer surveyCleanStatus, Integer surveyKindness) {
        this.reviewTitle = reviewTitle;
        this.reviewContent = reviewContent;
        this.surveySatisfaction = surveySatisfaction;
        this.surveySiteSize = surveySiteSize;
        this.surveyCleanStatus = surveyCleanStatus;
        this.surveyKindness = surveyKindness;
    }

    public static ReviewUpdateDto fromEntity(Review review){
        return ReviewUpdateDto.builder()
                .reviewTitle(review.getReviewTitle())
                .reviewContent(review.getReviewContent())
                .surveySatisfaction(review.getReviewSurvey().getSurveySatisfaction())
                .surveySiteSize(review.getReviewSurvey().getSurveySiteSize())
                .surveyCleanStatus(review.getReviewSurvey().getSurveyCleanStatus())
                .surveyKindness(review.getReviewSurvey().getSurveyKindness())
                .build();
    }
}
