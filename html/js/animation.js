document.addEventListener('DOMContentLoaded',()=>{
  animationActiveScrollEffect()/*애니메이션이 있는 위치로 스크롤해야 애니메이션이 동작하는 함수*/
})

function animationActiveScrollEffect(){
	const trigger = document.querySelectorAll("[class*='ani_']")/*ani_가 포함된 모든 클래스를 선택*/

	initEvent()
  
	function initEvent(){
		window.addEventListener('scroll',aniActive)
	}
	
	function aniActive(){
		let wSY =  window.scrollY + (window.innerHeight/1.1) ;/*나누는 값을 조정해서 애니메이션 동작 위치 제어*/
		for(const item of trigger){
			if(wSY>item.getBoundingClientRect().top + window.pageYOffset){
				item.classList.add('active')/*active 클래스를 줘서 동작*/
			}
		}
	}
}