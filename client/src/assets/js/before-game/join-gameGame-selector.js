 "use strict";
document.addEventListener("DOMContentLoaded", () => {
    document.querySelectorAll("aside li").forEach((element) => {
        element.addEventListener("click", gameSelectionToggle);
    });
});

function gameSelectionToggle(e) {
    e.target.classList.toggle("selected");
}
