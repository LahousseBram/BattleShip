"use strict";

document.addEventListener("DOMContentLoaded", init);
function init(){
    document.querySelectorAll("#gameSelector li").forEach((li) => {
        li.addEventListener("click", handleGameModeSelection);
    });
    document.querySelectorAll("#bordSize li").forEach((li) => {
        li.addEventListener("click", handleBoardSizeSelection);
    });

    document.querySelector("#continue-invite-player").addEventListener("click", saveGameMode);
    document.querySelector("#continue-invite-player").addEventListener("click", saveBoardSize);
}

function saveGameMode() {
    let gameMode = document.querySelector(".selectedGame").textContent;

    if (gameMode === "Regular") {
        gameMode = "simple";
    }

    saveToStorage("GameMode", gameMode.toLowerCase());
}

function saveBoardSize(){
    const size = document.querySelector(".selectedBoardSize").textContent;

     const rows = parseInt(size.slice(0,2));
     const cols = parseInt(size.slice(3,5));

    const boardSize = {
        rows : rows,
        cols : cols,
    };

    saveToStorage("BoardSize", boardSize);
}


function handleGameModeSelection(e) {
    document.querySelector("#gameSelector .selectedGame").classList.remove("selectedGame");
    e.target.classList.toggle("selectedGame");
}

function handleBoardSizeSelection(e){
    document.querySelector("#bordSize .selectedBoardSize").classList.remove("selectedBoardSize");
    e.target.classList.toggle("selectedBoardSize");
}
