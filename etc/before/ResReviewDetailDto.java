package com.project01_teamA.camping_lounge.dto.response;

import com.project01_teamA.camping_lounge.entity.BaseTime;
import com.project01_teamA.camping_lounge.entity.Comment;
import com.project01_teamA.camping_lounge.entity.Review;
import com.project01_teamA.camping_lounge.entity.ReviewPopularity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class ResReviewDetailDto {
    private Long reviewId;
    private String reviewTitle;
    private String reviewContent;
    private String reviewPostingDate;
    private String reviewEditingDate;
    private int reviewHit;
    private int reviewLikes;
    private Long memberId;
    private String memberName;
    private List<ResReviewFileUploadDto> reviewImages;
    private List<ResCommentDto> comment;


    @Builder
    public ResReviewDetailDto(List<ResCommentDto> comment, String memberName, Long memberId, Long reviewId, String reviewTitle, String reviewContent, String reviewPostingDate, String reviewEditingDate, int reviewHit,List<ResReviewFileUploadDto> reviewImages, int reviewLikes) {
        this.reviewId = reviewId;
        this.reviewTitle = reviewTitle;
        this.reviewContent = reviewContent;
        this.reviewPostingDate = reviewPostingDate;
        this.reviewEditingDate = reviewEditingDate;
        this.reviewHit = reviewHit;
        this.reviewLikes = reviewLikes;
        this.reviewImages = reviewImages;
        this.memberId = memberId;
        this.memberName = memberName;
        this.comment = comment;
    }

    public static ResReviewDetailDto fromEntity (Review review){
        return ResReviewDetailDto.builder()
                .reviewId(review.getReviewId())
                .reviewTitle(review.getReviewTitle())
                .reviewContent(review.getReviewContent())
                .reviewPostingDate(review.getReviewPostingDate())
                .reviewEditingDate(review.getReviewEditingDate())
                .reviewHit(review.getReviewPopularity().getReviewHit())
                .reviewLikes(review.getReviewPopularity().getReviewLikes())
                .comment(review.getComment().stream()
                        .map(ResCommentDto::fromEntity)
                        .collect(Collectors.toList()))
                .reviewImages(review.getReviewImages().stream()
                        .map(ResReviewFileUploadDto::fromEntity)
                        .collect(Collectors.toList()))
                .memberId(review.getMember().getId())
                .memberName(review.getMember().getName())
                .build();
    }
}
