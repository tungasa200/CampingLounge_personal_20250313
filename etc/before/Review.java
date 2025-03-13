package com.project01_teamA.camping_lounge.entity;

import com.project01_teamA.camping_lounge.entity.camp.Campsite;
import com.project01_teamA.camping_lounge.entity.reservation.Reservation;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Review extends BaseTime{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long reviewId;

    @Column(name = "review_title", nullable = false)
    private String reviewTitle;

    @Column(name = "review_content")
    private String reviewContent;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "pop_id")
    private ReviewPopularity reviewPopularity;

    @ManyToOne
    @JoinColumn(name = "survey_id")
    private ReviewSurvey reviewSurvey;

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @BatchSize(size = 10)
    private List<ReviewFiles> reviewImages = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = true)
    @BatchSize(size = 10)
    private List<Comment> comment = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "camp_id")
    private Campsite campsite;

    @ManyToOne
    @JoinColumn(name = "res_id")
    private Reservation reservation;

    @Builder
    public Review(Reservation reservation, Campsite campsite, List<Comment> comment, Member member, List<ReviewFiles> reviewImages, Long reviewId, String reviewTitle, String reviewContent, ReviewPopularity reviewPopularity, ReviewSurvey reviewSurvey, int reviewHit) {
        this.reviewId = reviewId;
        this.reviewTitle = reviewTitle;
        this.reviewContent = reviewContent;
        this.reviewImages = reviewImages;
        this.reviewPopularity = reviewPopularity;
        this.reviewSurvey = reviewSurvey;
        this.member = member;
        this.comment = comment;
        this.campsite = campsite;
        this.reservation = reservation;
    }

    // Review update method
    public void update(String reviewTitle, String reviewContent) {
        this.reviewTitle = reviewTitle;
        this.reviewContent = reviewContent;
    }


}
