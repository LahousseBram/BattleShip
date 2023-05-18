"use strict";

function handleFireMissile(e) {
    fetchFromServer(`/games/${_token.gameId}`).then(gameData => {
        let salvo = [];
        if (loadFromStorage("salvo")) {
            salvo = loadFromStorage("salvo");
        }
        e.target.classList.add("salvo-select");
        salvo.push(tileToCoord(e.target));
        saveToStorage("salvo", salvo);
        if (salvo.length >= parseInt(gameData.salvoSize[gameData.attackingCommander])) {
            fireMissile(salvo, gameData.defendingCommander);
            localStorage.removeItem("salvo");
        }
    }).catch(err => errorHandler(err));
}

function fireMissile(coords, defendingCommander) {
    const json = {salvo: coords};
        fetchFromServer(`/games/${_token.gameId}/fleet/${defendingCommander}/salvo`, 'POST', json).then((response) => {
            renderSalvo(response);
            document.querySelectorAll('#enemy-board .board-tile').forEach(el => el.removeEventListener('click', handleFireMissile));
            checkForTurn(_token.gameId);
        }).catch(err => errorHandler(err));
}

function renderSalvo(coords, boardName = 'enemy-board') {
    for (const coord in coords) {
        if (coords.hasOwnProperty(coord)) {
            const tile = coordToTile(coord, boardName);
            tile.classList.remove("salvo-select");
            if (coords[coord]) {
                tile.classList.add(`ship-hit`);
            } else {
                tile.classList.add(`missed-missile`);
            }
        }
    }
}


function coordToTile(str, boardName) {
    const chars = ['A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J','K','L','M','N','O',];
    const col = chars.indexOf(str[0]) + 1;
    const row = str.substring(2);
    return document.querySelector(`#${boardName} div[data-row="${row}"][data-col="${col}`);
}

function tileToCoord($tile) {
    const chars = ['A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J','K','L','M','N','O',];
    const row = $tile.dataset.row;
    const col = $tile.dataset.col;
    return `${chars[parseInt(col) - 1]}-${row}`;
}
