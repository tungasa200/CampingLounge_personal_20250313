function toggleAll(source) {
    let checkboxes = document.querySelectorAll('.checkbox');
    checkboxes.forEach(checkbox => checkbox.checked = source.checked);
}

function checkIndividual() {
    let checkboxes = document.querySelectorAll('.checkbox');
    let checkAll = document.getElementById('check-all');
    checkAll.checked = Array.from(checkboxes).every(checkbox => checkbox.checked);
}