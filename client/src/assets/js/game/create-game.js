"use strict";

function generateBoard(enemy) {
    const boardSize = loadFromStorage("BoardSize");
    const board = document.querySelectorAll(".gameboard");
    let html = ``;
    if (parseInt(boardSize.cols) === 10){
    for (const letter of "ABCDEFGHIJ") {
        html += `<div class="column-header">${letter}</div>`;
        board.forEach((smallBoard) => {
            smallBoard.classList.add("smallBoard");
        });
    }} else {
        for (const letter of "ABCDEFGHIJKLMNO") {
            html += `<div class="column-header">${letter}</div>`;
            board.forEach((bigBoard) => {
                bigBoard.classList.add("bigBoard");
            });
    }}
    for (let i = 0; i < parseInt(boardSize.rows); i++) {
        html += `<div class="row-header">${i + 1}</div>`;
        for (let j = 0; j < parseInt(boardSize.cols); j++) {
            html += `<div class="board-tile empty" data-row="${i + 1}" data-col="${j + 1}"></div>`;
        }
    }
    return html;
}

