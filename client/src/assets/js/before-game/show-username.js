"use strict";

document.addEventListener("DOMContentLoaded", init);

function init (){
    displayOwner();
}

function displayOwner(){
    const owner = document.querySelector(".owner");
    owner.textContent = loadFromStorage("UserName");
}
