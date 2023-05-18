"use strict";

document.addEventListener("DOMContentLoaded", () => {
    document.querySelector("#random").addEventListener("click", handleRandomButtonClick);
});

function handleRandomButtonClick(e) {
    const $playerBoard  = document.querySelector("#player-board");
    
    //get list of all boats
    const ships = fetchFromServer('/ships', 'GET');
    console.log(ships);
}