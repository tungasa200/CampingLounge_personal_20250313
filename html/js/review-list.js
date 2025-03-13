document.addEventListener('DOMContentLoaded',()=>{
    bestReviewSwiperEffect();
});

function bestReviewSwiperEffect(){
    var swiper = new Swiper('.best-review', {
        slidesPerView: 2, //보여지는 슬라이드 개수
        spaceBetween: 32, //슬라이드 사이 공간
        loop: true,
        loopAdditionalSlides: 1,
        freeMode: false,
        keyboard: {
            enabled: true,  //키보드 제어
        },
        autoplay: {
            delay: 4000, // 4초마다 슬라이드
            disableOnInteraction: false, //버튼 제어시 멈춤
        },
        speed : 1000,
        breakpoints: {
            768: {
                    slidesPerView: 1,  //
                    spaceBetween: 16, // 
                    centerMode:true,
                },
            
                },

        });
};