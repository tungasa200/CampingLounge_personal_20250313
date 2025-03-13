document.addEventListener("DOMContentLoaded",()=>{
    chattingSelectEffect();
})

function chattingSelectEffect(){
    const chat = document.querySelectorAll(".chat-list > li");

    chat.forEach(item => {
        item.addEventListener("click", function () {
            chat.forEach(li => li.classList.remove("select"));
            this.classList.add("select");
        });
    });
};