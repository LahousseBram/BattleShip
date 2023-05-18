"use strict";

function generateVisualAPIErrorInConsole(error){
    console.error('%c%s','background-color: red;color: white','! An error occurred while calling the API');
    console.table(error);
}

function errorHandler(error){
    generateVisualAPIErrorInConsole(error);
    document.querySelector(_config.errorHandlerSelector).insertAdjacentHTML("afterbegin", `<p class="error">Something went wrong <span class="closebtn">&times;</span></p>`);
    document.querySelector('.closebtn').addEventListener("click", deleteErr);
    setTimeout(() => {
        const $select = document.querySelector(".errormessages");
        $select.removeChild($select.lastChild);
    }, 5000);
}

function deleteErr(e) {
    e.target.parentElement.remove();
}
