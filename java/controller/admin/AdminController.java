package com.project01_teamA.camping_lounge.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project01_teamA.camping_lounge.dto.request.camp.CampUpdateDto;
import com.project01_teamA.camping_lounge.dto.request.camp.CampWriteDto;
import com.project01_teamA.camping_lounge.dto.request.member.MemberUpdateDto;
import com.project01_teamA.camping_lounge.dto.response.Reservation.ResReservationDto;
import com.project01_teamA.camping_lounge.dto.response.camp.ResCampDetailDto;
import com.project01_teamA.camping_lounge.dto.response.camp.ResCampWriteDto;
import com.project01_teamA.camping_lounge.dto.response.member.MemberResponseDto;
import com.project01_teamA.camping_lounge.dto.response.review.ResCommentDto;
import com.project01_teamA.camping_lounge.dto.response.review.ResReviewDetailDto;
import com.project01_teamA.camping_lounge.entity.Member;
import com.project01_teamA.camping_lounge.repository.ChatMessageRepository;
import com.project01_teamA.camping_lounge.repository.MemberRepository;
import com.project01_teamA.camping_lounge.repository.camp.CampRepository;
import com.project01_teamA.camping_lounge.repository.reservation.ReservationRepository;
import com.project01_teamA.camping_lounge.repository.review.CommentRepository;
import com.project01_teamA.camping_lounge.repository.review.ReviewRepository;
import com.project01_teamA.camping_lounge.service.CommentService;
import com.project01_teamA.camping_lounge.service.MemberService;
import com.project01_teamA.camping_lounge.service.SecurityKeyService;
import com.project01_teamA.camping_lounge.service.camp.CampService;
import com.project01_teamA.camping_lounge.service.reservation.ReservationService;
import lombok.NoArgsConstructor;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@Slf4j
public class AdminController {

    private final MemberService memberService;
    private final SecurityKeyService securityKeyService;
    private final MemberRepository memberRepository;
    private final CampRepository campRepository;
    private final ReservationRepository reservationRepository;
    private final ReservationService reservationService;
    private final ReviewRepository reviewRepository;
    private final CommentRepository commentRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final com.project01_teamA.camping_lounge.service.reviewService.ReviewService reviewService;
    private final CommentService commentService;
    private final CampService campService;


//    @GetMapping("/members")
//    public ResponseEntity<List<Member>> getAllMembers() {
//        List<Member> members = memberService.getAllMembers();
//        return ResponseEntity.status(HttpStatus.OK).body(members);
//    }

    @GetMapping("/members")
    public ResponseEntity<Page<MemberResponseDto>> getAllMembers(
              @PageableDefault(size= 10, sort="id", direction = Sort.Direction.DESC) Pageable pageable) {
        System.out.println("Received sort: " + pageable.getSort());
        Page<MemberResponseDto> listDto = memberService.getAllMembers(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(listDto);
    }

    @DeleteMapping("/delete/{memberId}")
    public ResponseEntity<String> deleteMember(@PathVariable Long memberId) {
        memberService.deleteMember(memberId);
        return ResponseEntity.status(HttpStatus.OK).body("유저 삭제 성공");
    }

    @PutMapping("/update/{memberId}")
    public ResponseEntity<String> updateMember(@PathVariable Long memberId, @RequestBody MemberUpdateDto memberUpdateDto) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new IllegalArgumentException("회원 정보를 찾을 수 없습니다."));
        memberService.userUpdate(member, memberUpdateDto);
        return ResponseEntity.status(HttpStatus.OK).body("유저 정보 수정 성공");
    }

    @GetMapping("/review")
    public ResponseEntity<Page<ResReviewDetailDto>> getAllReviews(
            @PageableDefault(size= 10, sort="reviewId", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<ResReviewDetailDto> listDto = reviewService.getAllReviewsForAdmin(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(listDto);
    }

    @DeleteMapping("/review/{reviewId}/delete")
    public ResponseEntity<String> deleteReview(@PathVariable Long reviewId) {
        reviewService.detailReviews(reviewId);
        return ResponseEntity.status(HttpStatus.OK).body("리뷰 삭제 성공");
    }

    @GetMapping("/comment")
    public ResponseEntity<Page<ResCommentDto>> getAllComments(
            @PageableDefault(size= 10, sort="commentId", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<ResCommentDto> listDto = commentService.getAllCommentsForAdmin(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(listDto);
    }

    @DeleteMapping("/comment/{commentId}/delete")
    public ResponseEntity<String> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComments(commentId);
        return ResponseEntity.status(HttpStatus.OK).body("댓글 삭제 성공");
    }

    //어드민 페이지 요청
    //캠핑장 등록
    @PostMapping(value = "/admin/camp/write", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ResCampWriteDto> write(
            @RequestPart("campDto") String campDtoJson,
            @RequestPart(value = "thumb", required = false) List<MultipartFile> thumb,
            @RequestPart(value = "images", required = false) List<MultipartFile> images) throws IOException {

        Thread currentThread = Thread.currentThread();
        log.info("현재 실행중인 스레드" + currentThread.getName());
        //제이슨 데이터를 자바 객체로 매핑
        CampWriteDto campDto = new ObjectMapper().readValue(campDtoJson, CampWriteDto.class);

        // DTO에 파일 정보 추가
        campDto.setThumb(thumb);
        campDto.setImages(images);

        ResCampWriteDto saveCampDto = campService.write(campDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saveCampDto);
    }

    @GetMapping(value = "/admin/camp/openapi/{rowNum}")
    public String CampDataFromOpenApi(@PathVariable Long rowNum) {
        campService.CampDataFromOpenApi(rowNum);
        return "고캠핑 공공 API 연동에 성공했습니다.";
    }

    //캠핑장 상세
    @GetMapping("/admin/camp/{campId}")
    public ResponseEntity<ResCampDetailDto> adminCampDetail(@PathVariable("campId") Long campId) {
        ResCampDetailDto findCampDto = campService.detail(campId);
        return ResponseEntity.status(HttpStatus.OK).body(findCampDto);
    }

    // 캠핑장 수정
    @PatchMapping("/admin/camp/{campId}/update")
    public ResponseEntity<ResCampDetailDto> update(
            @PathVariable("campId") Long campId,
            @RequestBody CampUpdateDto campDTO) {
        ResCampDetailDto updateCampdDTO = campService.update(campId, campDTO);
        return ResponseEntity.status(HttpStatus.OK).body(updateCampdDTO);
    }

//    //체크한 캠핑장 삭제
//    @DeleteMapping("/admin/camp/delete")
//    public ResponseEntity<String> deleteCheck (@RequestBody List<Long> campId){
//        campService.deleteChecked(campId);
//        return ResponseEntity.status(HttpStatus.OK).build();
//    }

    // 캠핑장 상세에서 캠핑장 삭제
    @DeleteMapping("/admin/camp/{campId}/delete")
    public ResponseEntity<String> delete (@PathVariable Long campId){
        campService.delete(campId);
        return ResponseEntity.status(HttpStatus.OK).body("캠프 삭제 성공");
    }



    // 보안키 기능

    @GetMapping("/securityKey")
    public ResponseEntity<String> getSecurityKey() {
        return ResponseEntity.status(HttpStatus.OK).body(securityKeyService.getSecurityKey());
    }

//    @GetMapping("/regenerateKey")
//    public ResponseEntity<String> regenerateKey() {
//        securityKeyService.generateKey();
//        return ResponseEntity.status(HttpStatus.OK).body("Key Regenerated");
//    }

    @GetMapping("/dashboard/member")
    public ResponseEntity<Long> getMemberCount() {
        Long memberCount = memberRepository.count();
        System.out.println("전체 맴버 수" + memberCount);
        return ResponseEntity.status(HttpStatus.OK).body(memberCount);
    }
    @GetMapping("/dashboard/camp")
    public ResponseEntity<Long> getCampSiteCount() {
        Long campCount = campRepository.count();
        System.out.println("전체 캠프 수" + campCount);
        return ResponseEntity.status(HttpStatus.OK).body(campCount);
    }
    @GetMapping("/dashboard/res")
    public ResponseEntity<Long> getReservationCount() {
        Long resCount = reservationRepository.count();
        System.out.println("전체 예약 수" + resCount);
        return ResponseEntity.status(HttpStatus.OK).body(resCount);
    }

    @GetMapping("/dashboard/review")
    public ResponseEntity<Long> getReviewCount() {
        Long reviewCount = reviewRepository.count();
        System.out.println("전체 리뷰 수" + reviewCount);
        return ResponseEntity.status(HttpStatus.OK).body(reviewCount);
    }

    @GetMapping("/dashboard/comment")
    public ResponseEntity<Long> getCommentCount() {
        Long commentCount = commentRepository.count();
        System.out.println("전체 댓글 수" + commentCount);
        return ResponseEntity.status(HttpStatus.OK).body(commentCount);
    }

    @GetMapping("/dashboard/chatting")
    public ResponseEntity<Long> getChattingCount() {
        Long chattingCount = chatMessageRepository.count();
        System.out.println("전체 채팅 수" + chattingCount);
        return ResponseEntity.status(HttpStatus.OK).body(chattingCount);
    }

    @GetMapping("/member/disabled")
    public ResponseEntity<Long> getDisabledMemberCount() {
        Long disabledMemberCount = memberRepository.countByEnableFalse();
        System.out.println("비활성화된 맴버 수: " + disabledMemberCount);
        return ResponseEntity.status(HttpStatus.OK).body(disabledMemberCount);
    }

    @GetMapping("/member/gender")
    public ResponseEntity<Map<String, Long>> getGenderRatio() {
        Long maleCount = memberRepository.countByGender("남자");
        Long femaleCount = memberRepository.countByGender("여자");

        Map<String, Long> genderRatio = new HashMap<>();
        genderRatio.put("male", maleCount);
        genderRatio.put("female", femaleCount);

        System.out.println("남성 회원 수: " + maleCount);
        System.out.println("여성 회원 수: " + femaleCount);

        return ResponseEntity.status(HttpStatus.OK).body(genderRatio);
    }

    //예약 탭
    @GetMapping("/reservation")
    public ResponseEntity<Page<ResReservationDto>> getAllReservations(
            @PageableDefault(size= 10, sort="id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<ResReservationDto> listDto = reservationService.getAllReservations(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(listDto);
    }
    
    @DeleteMapping("/reservation/{reservationId}/delete")
    public ResponseEntity<String> deleteReservation(@PathVariable Long reservationId) {
        return ResponseEntity.status(HttpStatus.OK).body("예약 삭제 성공");
    }


}
