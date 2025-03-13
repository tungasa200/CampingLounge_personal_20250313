document.addEventListener('DOMContentLoaded', ()=>{
    headerShowHideEffect()
    subPageEffect()
})

function headerShowHideEffect(){
    const gnbBtn = document.querySelector('.gnb_wrap');
    const nav = document.querySelector('#nav');
    const logo = document.querySelector("#header .logo_wrap")
    let isOpen = false;


    initEvent()

    function initEvent(){
        gnbBtn.addEventListener("click", menuShowEffect);
    }

    function menuShowEffect(){
        if(! isOpen){
            isOpen = true;
            gnbBtn.classList.add("open");
            logo.classList.add("open");
            gsap.to(nav, { y : 0 + "%", duration:0.5})
            
        } else 
        {
            isOpen = false;
            gnbBtn.classList.remove("open");
            logo.classList.remove("open");
            gsap.to(nav, { y : -100 + "%", duration:0.5 })
        }
    }
}

function subPageEffect(){
    const main = document.querySelector('#main')
    if(main.classList.contains("sub")){
        lisSwiperEffect()
    }
}

function lisSwiperEffect(){
    var swiper = new Swiper('.swiper-container', {
      slidesPerView: 1, //보여지는 슬라이드 개수
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
            slidesPerView: 2,  //
            spaceBetween: 16, // 
            centerMode:true,
          },
     
        },
  
    });
    $('.swiper-container').hover(function() {    swiper.autoplay.stop();}, function(){    swiper.autoplay.start();});
    
  }