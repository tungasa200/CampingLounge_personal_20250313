package com.project01_teamA.camping_lounge.controller.camp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project01_teamA.camping_lounge.dto.request.camp.CampUpdateDto;
import com.project01_teamA.camping_lounge.dto.request.camp.CampWriteDto;
import com.project01_teamA.camping_lounge.dto.response.camp.ResCampDetailDto;
import com.project01_teamA.camping_lounge.dto.response.camp.ResCampListDto;
import com.project01_teamA.camping_lounge.dto.response.camp.ResCampWriteDto;
import com.project01_teamA.camping_lounge.dto.response.review.ResReviewListDto;
import com.project01_teamA.camping_lounge.service.camp.CampService;
import com.project01_teamA.camping_lounge.service.reviewService.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/camp")
@Slf4j
public class CampController {
    private final CampService campService;
    private final ReviewService reviewService;

    //전체보기
    @GetMapping("")
    public ResponseEntity<Page<ResCampListDto>> campList(
            @PageableDefault(size = 9, sort = "id", direction = Sort.Direction.DESC)Pageable pageable){
        Page<ResCampListDto> listDto = campService.getAllCamps(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(listDto);
    }
    
    //서치, 필터
    @GetMapping("/search")
    public ResponseEntity<Page<ResCampListDto>> searchCamp(
            @RequestParam(required = false) String search,
            @RequestParam(required = false)List<String> filters,
            @PageableDefault(size = 9, sort = "id", direction = Sort.Direction.DESC)Pageable pageable){
            Page<ResCampListDto> listDto = campService.getFilteredCamps(search, filters, pageable);
            return ResponseEntity.status(HttpStatus.OK).body(listDto);
    }

    //캠핑장 상세
    @GetMapping("/{campId}")
    public ResponseEntity<ResCampDetailDto> detail(@PathVariable("campId") Long campId) {
        ResCampDetailDto findCampDto = campService.detail(campId);
        return ResponseEntity.status(HttpStatus.OK).body(findCampDto);
    }

    @GetMapping("/uplike")
    public void upLikeCount(@RequestParam Long campId) {
        ResCampDetailDto findCampDto = campService.upLikeCount(campId);
    }

    @GetMapping("/downlike")
    public void downLikeCount(@RequestParam Long campId) {
        ResCampDetailDto findCampDto = campService.downLikeCount(campId);
    }

    @GetMapping("/{campId}/review")
    public ResponseEntity<Page<ResReviewListDto>> getCampReivew(
            @PathVariable("campId") Long campId,
            @PageableDefault(size = 9, sort = "id", direction = Sort.Direction.DESC)Pageable pageable){
        Page<ResReviewListDto> listDto = reviewService.findByCampId(campId,pageable);
        return ResponseEntity.status(HttpStatus.OK).body(listDto);
    }




}
