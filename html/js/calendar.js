document.addEventListener('DOMContentLoaded',()=>{
    calendarEffect()
})

function calendarEffect(){
    let currentDate = new Date();
    let activeDay = null;
    const today = new Date();

    const prev = document.querySelector('.cal-prev');
    const next = document.querySelector('.cal-next');

    
    initEvent()

    function initEvent(){
        renderCalendar();
        calendarMove();
    }
    
    function renderCalendar() {

        const monthYear = document.getElementById("monthYear");
        const daysContainer = document.getElementById("calendarDays");
        daysContainer.innerHTML = "";
        
        const year = currentDate.getFullYear();
        const month = currentDate.getMonth();
        monthYear.textContent = `${year}년 ${month + 1}월`;

        const firstDay = new Date(year, month, 1).getDay();
        const lastDate = new Date(year, month + 1, 0).getDate();
        const prevLastDate = new Date(year, month, 0).getDate();
        
        for (let i = firstDay - 1; i >= 0; i--) {
            const dayElement = document.createElement("div");
            const dayText = document.createElement("p");
            dayElement.classList.add("day", "inactive");
            dayText.textContent = prevLastDate - i;
            daysContainer.appendChild(dayElement);
            dayElement.appendChild(dayText);
        }
        
        for (let i = 1; i <= lastDate; i++) {
            const dayElement = document.createElement("div");
            const dayText = document.createElement("p");
            dayElement.classList.add("day");
            dayText.textContent = i;
            
            if (year === today.getFullYear() && month === today.getMonth() && i === today.getDate()) {
                dayElement.classList.add("today");
            }
            
            if (year < today.getFullYear() || (year === today.getFullYear() && month < today.getMonth()) || (year === today.getFullYear() && month === today.getMonth() && i < today.getDate())) {
                dayElement.classList.add("inactive");
            } else {
                dayElement.addEventListener("click", () => activateDay(dayElement, year, month + 1, i));
            }
            daysContainer.appendChild(dayElement);
            dayElement.appendChild(dayText);
        }
    }

    function activateDay(dayElement, year, month, day) {
        if (activeDay) {
            activeDay.classList.remove("active");
        }
        dayElement.classList.add("active");
        activeDay = dayElement;
        document.getElementById("selectedDate").value = `${year}-${month.toString().padStart(2, '0')}-${day.toString().padStart(2, '0')}`;
    }

    function calendarMove(){       
        prev.addEventListener('click', prevMonth);
        next.addEventListener('click', nextMonth);
    };

    function prevMonth() {
        if (currentDate.getFullYear() === today.getFullYear() && currentDate.getMonth() === today.getMonth()) return;
        currentDate.setMonth(currentDate.getMonth() - 1);
        renderCalendar();
    }

    function nextMonth() {
        currentDate.setMonth(currentDate.getMonth() + 1);
        renderCalendar();
    }

}