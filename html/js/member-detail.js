document.addEventListener('DOMContentLoaded',()=>{
    memDetailTapShowHideEffect();
    swiperEffect()
});

function memDetailTapShowHideEffect(){
    const btn = document.querySelectorAll('.tap-btn');
    const tap = document.querySelectorAll('.act-tap');

    initEvent()

    function initEvent(){
        btn.forEach((trigger)=>{
            trigger.addEventListener('click', ()=>{
                tap.forEach(item => item.classList.remove('active'));
                trigger.parentElement.parentElement.classList.add('active');
            });
        })
    }
}

function swiperEffect(){
    reviewSwiperEffect()
}

function reviewSwiperEffect(){
    var swiper = new Swiper('.review-swiper', {
        slidesPerView: 2, //보여지는 슬라이드 개수
        spaceBetween: 32, //슬라이드 사이 공간
        loop: true,
        loopAdditionalSlides: 1,
        freeMode: false,
        keyboard: {
            enabled: true,  //키보드 제어
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