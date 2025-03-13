document.addEventListener('DOMContentLoaded',()=>{
    filterSelectEffect()
});

function filterSelectEffect(){
    const filter = document.querySelectorAll('.filter > .item');

    filter.forEach(item => {
        item.addEventListener('click',()=> item.classList.toggle('active'))});
    
}