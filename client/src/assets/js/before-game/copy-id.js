"use strict";

document.addEventListener("DOMContentLoaded", init);

function init(){
    const copyText = document.querySelector("#copy-id");
    copyText.addEventListener("click", copyId);
}

function copyId(e){
    e.preventDefault();
    const copyText = document.getElementById("game-id");

    copyText.select();

    navigator.clipboard.writeText(copyText.value);

    changeButtonContent();
}

function changeButtonContent() {
    const button = document.querySelector("#copy-id");
    button.innerText = "COPIED!";

    setInterval(() => {
        button.innerText = "Copy ID!";
    }, 3000);
}
