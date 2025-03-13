document.addEventListener('DOMContentLoaded',()=>{
    loginOpenEffect();
    menuShowHideEffect();
  });

  function menuShowHideEffect(){
    const header = document.querySelector('#header');
    var lastScrollTop = 0;
	var delta = 5;
	var didScroll;

    window.onscroll = function(e) {
		didScroll = true;
	};

    setInterval(function(){
		if(didScroll==true){
			hasScrolled();
			didScroll = false;
		}
	}, 250);

    function hasScrolled(){
        var nowScrollTop = window.scrollY;
        if(Math.abs(lastScrollTop - nowScrollTop) <= delta){
            return;
        }
        
        if(nowScrollTop > lastScrollTop && nowScrollTop >= 10 ){
            //Scroll down
            header.classList.add('active')
                
        }
        if(nowScrollTop <= 100 ){
            //Scroll down
            header.classList.remove('active')
        }

        // if(nowScrollTop > lastScrollTop ){
        //     //Scroll down
        //     header.classList.add('slide_up')
        // }
        // else
        // if(nowScrollTop<= lastScrollTop){
        //     //Scroll up
        //     header.classList.remove('slide_up')
        // }
    
    lastScrollTop = nowScrollTop;
    }
};

function loginOpenEffect(){
    const screen = document.querySelector('#screen');
    const loginBtn = document.querySelector('.login-btn');

    loginBtn.addEventListener('click',()=>{
        screen.classList.toggle('active');
    })
}