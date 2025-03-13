document.addEventListener('DOMContentLoaded',()=>{
    modalShowHideEffect();
    tapmenuShowHideEffect();
});

function modalShowHideEffect(){
    const modal = document.querySelector('#modal');
    const dark = document.querySelector('#dark-area');
    const btn = document.querySelectorAll('.modal-btn');
    const close = document.querySelector('.close-btn');
    let isActive = false;

    initEvent()

    function initEvent(){        
        btn.forEach(item => {
            item.addEventListener('click',modalShowHide); 
        });           
        dark.addEventListener('click',modalShowHide);
        close.addEventListener('click',modalShowHide);
    }

    function modalShowHide(){
        if(!isActive){
            modal.classList.toggle('active');
            dark.classList.toggle('active');      
            isActive = true;  
        }else{
            modal.classList.toggle('active');
            dark.classList.toggle('active');
        }
    }
}

function tapmenuShowHideEffect(){
    const btn01 = document.querySelector('.tap01');
    const btn02 = document.querySelector('.tap02');

    const tap01 = document.querySelector('.camp-info');
    const tap02 = document.querySelector('.camp-review');

    initEvnet();

    function initEvnet(){
        btn01.addEventListener('click', reviewShow)
        btn02.addEventListener('click', infoShow)
    }

    function reviewShow(){
        btn01.classList.remove('active');
        tap01.classList.remove('active');

        btn02.classList.add('active');
        tap02.classList.add('active');
    }

    function infoShow(){
        btn02.classList.remove('active');
        tap02.classList.remove('active');

        btn01.classList.add('active');
        tap01.classList.add('active');
    }
}