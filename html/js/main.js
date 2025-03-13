document.addEventListener("DOMContentLoaded",()=>{
    mainSwiperEffect()
});

function mainSwiperEffect(){
    mainSwiper01()
    mainSwiper02()
    mainSwiper03()
}

function mainSwiper01(){
    var swiper = new Swiper('.main-swiper01-container', {
        slidesPerView: 3, //보여지는 슬라이드 개수
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
         navigation: {
            nextEl: ".swiper01-next", 
            prevEl: ".swiper01-prev",
        },
        speed : 1000,
        breakpoints: {
            768: {
                    slidesPerView: 2,  //
                    spaceBetween: 16, // 
                    centerMode:true,
                },
            
                },

        });
}
function mainSwiper02(){
    var swiper = new Swiper('.main-swiper02-container', {
        slidesPerView: 3, //보여지는 슬라이드 개수
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
         navigation: {
            nextEl: ".swiper02-next", 
            prevEl: ".swiper02-prev",
        },
        speed : 1000,
        breakpoints: {
            768: {
                    slidesPerView: 2,  //
                    spaceBetween: 16, // 
                    centerMode:true,
                },
            
                },

        });
}
function mainSwiper03(){
    var swiper = new Swiper('.main-swiper03-container', {
        slidesPerView: 3, //보여지는 슬라이드 개수
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
         navigation: {
            nextEl: ".swiper03-next", 
            prevEl: ".swiper03-prev",
        },
        speed : 1000,
        breakpoints: {
            768: {
                    slidesPerView: 2,  //
                    spaceBetween: 16, // 
                    centerMode:true,
                },
            
                },

        });
}