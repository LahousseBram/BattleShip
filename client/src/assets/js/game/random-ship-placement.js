"use strict";

function handlePlaceRandomShips(e) {
    clearBoard();
    fetchFromServer('/ships', 'GET').then(response => placeRandomShips(response.ships)).catch(err => errorHandler(err));
}

function placeRandomShips(ships) {
    for (const ship of ships) {
        const dir = rng(0, 4);
        let x = rng(1, 11);
        let y = rng(1, 11);

        while (!checkValidShipLocation(x, y, dir, ship)) {
            x = rng(1, 11);
            y = rng(1, 11);
        }
        renderShipImages(y, x, dir, ship, document.querySelector('#player-board'));
        placeShip(y, x, dir, ship);
    }
}

function checkValidShipLocation(x, y, dir, ship) {
    for (let i = 0; i < ship.size; i++) {
        //if direction is north (3) or west (2) do -i else do +i on the corresponding axis
        const dx = dir === 2 ? -i : dir === 0 ? i : 0;
        const dy = dir === 3 ? -i : dir === 1 ? i : 0;
        if (isInvalidCoordinate(x + dx, y + dy)) {
            return false;
        }
    }
    return true;
}

function isInvalidCoordinate(x, y) {
    const $tile = document.querySelector(`#player-board div[data-row="${y}"][data-col="${x}"]`);
    const xOutOfBounds = x < 1 || x > 10;
    const yOutOfBounds = y < 1 || y > 10;
    return yOutOfBounds || xOutOfBounds || !$tile.classList.contains('empty');
}

function rng(min, max) {
    return Math.floor(Math.random() * (max - min) ) + min;
}
