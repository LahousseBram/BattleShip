"use strict";

function handleClearBoard(e) {
    clearBoard();
}

function clearBoard() {
    const $playerBoard = document.querySelector("#player-board");

    $playerBoard.querySelectorAll(".board-tile").forEach((tile) => {
        tile.className = "board-tile empty";
    });

    fetchFromServer('/ships', 'GET').then(response => renderSelectShips(response.ships)).catch(errorHandler);
}
