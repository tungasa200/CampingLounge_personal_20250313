document.addEventListener("DOMContentLoaded",()=>{
    profileThumbShowEffect();
});

function profileThumbShowEffect(){
    const input = document.querySelector("#profile-image");

    initEvent()

    function initEvent(){
        input.addEventListener("change", function(event){
            const file = event.target.files[0];
            const thumb = document.querySelector(".profile-thumb > img");
    
            if(file){
                const reader = new FileReader();
                reader.onload = function(e){
                    thumb.src = e.target.result;
                    thumb.style.display="block";
                };
                reader.readAsDataURL(file);
            }else{
                thumb.style.display="none";
            }
        }    );
    }  
}
