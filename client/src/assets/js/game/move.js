'use strict';

function handleMove(e) {
    const shipName = /[a-z]*(?=-[0-9]\b)/.exec(e.target.className)[0];
    if (!shipName) {
        return;
    }
    fetchFromServer(`/games/${_token.gameId}/fleet/${loadFromStorage("UserName")}/${shipName}/location`, "PATCH").then(response => {
        renderMove(response.ship, response.location);
        checkForTurn(_token.gameId);
    }).catch(err => errorHandler(err));
}

function renderMove(ship, locations) {
    const deg = /deg-\d*/.exec(document.querySelector(`div[class*="${ship.name}"]`).className)[0];
    document.querySelectorAll(`div[class*="${ship.name}"]`).forEach(element =>  {
        element.className = "board-tile empty";
    });
    placeShipByLocations(ship, locations, deg);
}
