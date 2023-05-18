"use strict";

function handlePreviewPlacement(e) {
    previewPlacement(e.target);
}

function previewPlacement(element) {
    if (element === null) {
        return;
    } // This is for when the mouse is positioned between tiles

    const ship = loadFromStorage('selected-ship');
    const row = element.dataset.row;
    const col = element.dataset.col;
    const $playerBoard = document.querySelector('#player-board');

    const currentRotation = loadFromStorage("CurrentRotation");

    try {
        clearAllRedTiles($playerBoard); // clear all red tiles before each move
        renderShipImages(row, col, currentRotation, ship, $playerBoard);
    } catch (error) {
        makeInvalid($playerBoard.querySelector(".empty:hover"));
    }
}

function renderShipImages(row, col, direction, ship, $playerBoard) {
    for (let i = 0; i < ship.size; i++) {
        switch (direction % 4) {
            case 0:
                previewPlacementHelper(`div.empty[data-row="${row}"][data-col="${parseInt(col) + i}"]`, "deg-0", i, ship, $playerBoard);
                break;
            case 1:
                previewPlacementHelper(`div.empty[data-row="${parseInt(row) + i}"][data-col="${col}"]`, "deg-90", i, ship, $playerBoard);
                break;
            case 2:
                previewPlacementHelper(`div.empty[data-row="${row}"][data-col="${parseInt(col) - i}"]`, "deg-180", i, ship, $playerBoard);
                break;
            case 3:
                previewPlacementHelper(`div.empty[data-row="${parseInt(row) - i}"][data-col="${col}"]`, "deg-270", i, ship, $playerBoard);
                break;
            default:
                break;
        }
    }
}

function previewPlacementHelper(name, deg, i, ship, $playerBoard) {
    $playerBoard.querySelector(name).classList.toggle(`${ship.name}-${i + 1}`);
    $playerBoard.querySelector(name).classList.toggle(deg);
}

function placementHelper(name) {
    const $playerBoard = document.querySelector('#player-board');
    $playerBoard.querySelector(name).classList.remove("empty");
}

function checkForEmptySquare(row, col) {
    return !document.querySelector(`#player-board .board-tile[data-row="${row}"][data-col="${col}"]`).classList.contains("empty");
}

function makeInvalid(element) {
    if (element === null) {
        return false;
    }
    element.classList.toggle("invalid");
    return true;
}

function handlePlaceShip(e) {
    if (e.target.classList.contains("invalid")) {
        return;
    }
    const row = e.target.dataset.row;
    const col = e.target.dataset.col;
    const currentRotation = loadFromStorage("CurrentRotation");
    const selectedShip = 'selected-ship';
    const ship = loadFromStorage(selectedShip);
    placeShip(row, col, currentRotation, ship);
    saveToStorage("CurrentRotation", 0); // clear current rotation from local storage
    saveToStorage(selectedShip, {}); // clear the current ship selection from local storage
}

function placeShip(row, col, direction, ship) {
    //remove placed ship from selectable ships
    document.querySelector(`#${ship.name}`).remove();
    removeEventListeners();

    for (let i = 0; i < ship.size; i++) {
        switch (direction % 4) {
            case 0:
                placementHelper(`div.empty[data-row="${row}"][data-col="${parseInt(col) + i}"]`);
                break;
            case 1:
                placementHelper(`div.empty[data-row="${parseInt(row) + i}"][data-col="${col}"]`);
                break;
            case 2:
                placementHelper(`div.empty[data-row="${row}"][data-col="${parseInt(col) - i}"]`);
                break;
            case 3:
                placementHelper(`div.empty[data-row="${parseInt(row) - i}"][data-col="${col}"]`);
                break;
            default:
                break;
        }
    }
}

function removeEventListeners() {
    document.querySelectorAll(`.board-tile`).forEach((el) => {
        el.removeEventListener('mouseenter', handlePreviewPlacement);
        el.removeEventListener('mouseleave', handlePreviewPlacement);
        el.removeEventListener('click', handlePlaceShip);
    });
}

function placeShipByLocations(ship, locations, deg) {
    for (const location of locations) {
        const tile = coordToTile(location, "player-board");
        tile.classList.remove('empty');
        tile.classList.add(deg);
        tile.classList.add(`${ship.name}-${locations.indexOf(location) + 1}`);
    }
}

function clearAllRedTiles($playerBoard) {
    $playerBoard.querySelectorAll(".board-tile.invalid").forEach(tile => tile.classList.remove("invalid"));
}
