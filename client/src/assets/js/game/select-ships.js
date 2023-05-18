"use strict";

function renderSelectShips(ships) {
    let html = ``;
    for (const ship of ships) {
        html += `<img class="select-ship" id="${ship.name}" data-size="${ship.size}" src="../assets/img/ships/${ship.name}.png" alt="${ship.name}">`;
    }
    document.querySelector('#ships').innerHTML = html;
    addSelectShipsEventListeners();
}


function addSelectShipsEventListeners() {
    document.querySelectorAll('.select-ship').forEach(el => el.addEventListener('click', selectShip));
}

function selectShip(e) {
    saveToStorage("selected-ship", {name: e.target.id, size: e.target.dataset.size});
    const $playerBoard = document.querySelector('#player-board');
    $playerBoard.querySelectorAll('.board-tile.empty').forEach(el => {
        el.addEventListener('mouseenter', handlePreviewPlacement);
        el.addEventListener('mouseleave', handlePreviewPlacement);
        el.addEventListener('click', handlePlaceShip);
    });
}
