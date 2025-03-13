package com.project01_teamA.camping_lounge.dto.request;

import com.project01_teamA.camping_lounge.entity.Member;
import com.project01_teamA.camping_lounge.entity.Review;
import com.project01_teamA.camping_lounge.entity.ReviewSurvey;
import com.project01_teamA.camping_lounge.entity.camp.Campsite;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReviewWriteDto {
    private String reviewTitle;
    private String reviewContent;

    private Long surveyId;
    private Integer surveySatisfaction;
    private Integer surveySiteSize;
    private Integer surveyCleanStatus;
    private Integer surveyKindness;

    private Long memberId;

    private Long reservationId;

    private Long campId;

    @Builder
    public ReviewWriteDto(Long memberId, Long campId, Long reservationId, String reviewTitle, String reviewContent, Long surveyId, Integer surveySatisfaction, Integer surveySiteSize, Integer surveyCleanStatus, Integer surveyKindness) {
        this.reviewTitle = reviewTitle;
        this.reviewContent = reviewContent;
        this.surveyId = surveyId;
        this.surveySatisfaction = surveySatisfaction;
        this.surveySiteSize = surveySiteSize;
        this.surveyCleanStatus = surveyCleanStatus;
        this.surveyKindness = surveyKindness;
        this.reservationId = reservationId;
        this.campId = campId;
        this.memberId = memberId;
    }

    public static ReviewWriteDto fromEntity(Review review, ReviewSurvey reviewSurvey) {
        return ReviewWriteDto.builder()
                .reviewTitle(review.getReviewTitle())
                .reviewContent(review.getReviewContent())
                .surveyId(reviewSurvey.getSurveyId())
                .surveySatisfaction(reviewSurvey.getSurveySatisfaction())
                .surveySiteSize(reviewSurvey.getSurveySiteSize())
                .surveyCleanStatus(reviewSurvey.getSurveyCleanStatus())
                .surveyKindness(reviewSurvey.getSurveyKindness())
                .reservationId(review.getReservation().getId())
                .campId(review.getCampsite().getId())
                .memberId(review.getMember().getId())
                .build();
    }

    public Review toEntity(Member member) {
        return Review.builder()
                .member(member)
                .reviewTitle(this.reviewTitle)
                .reviewContent(this.reviewContent)
                .build();
    }
}
