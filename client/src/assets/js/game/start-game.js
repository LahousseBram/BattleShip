'use strict';

function getEnemyName() {
    let commanderName;
    fetchFromServer(`/games/${_token.gameId}`).then(gameData => {
        commanderName = gameData.defendingCommander === localStorage.getItem("UserName") ? gameData.defendingCommander : gameData.attackingCommander;
    }).catch(err => errorHandler(err));
    localStorage.setItem("EnemyName", commanderName);
}

function startPublicGame(e){
    createGame("group34", false);
}

function removeOtherButtons() {
    document.querySelector("#public-game").remove();
    document.querySelector("#join-game-form").remove();
    document.querySelector("#private-game").remove();
}

function startPrivateGame(e){
    createGame(generatePrefix(),true);
    removeOtherButtons();
}

function startJoinGame(){
    const gameId = document.querySelector("#join-game-input").value;
    createGame(gameId, false);
    }

function createGame(prefix, privateGame) {
    fetchFromServer('/ships', 'GET').then(shipsResponse => {
        fetchFromServer('/games', 'POST', generateGameData(shipsResponse.ships, prefix))
            .then(response => {
                _token = {
                    playerToken: response.playerToken,
                    gameId: response.gameId,
                };

                if (privateGame){
                    displayGameId(prefix);
                } else {
                    removeGameTypeButtons();
                }
                getEnemyName();
                renderTurnTypeSelectors();
                addHistoryButton();
                checkForOtherPlayer(_token.gameId);
            }).catch(error => errorHandler(error));
    }).catch(errorHandler);
}


function generatePrefix(){
    const userName = loadFromStorage("UserName");
    let prefix = Math.floor(1000+ Math.random() * 9000);
    prefix = userName + prefix.toString();
    return prefix;
}

function displayGameId(gameId){
    const form = document.querySelector("#privateGameForm");
    const id = document.querySelector("#game-id");
    form.classList.remove("hidden");
    id.value = gameId;
}

function removeGameTypeButtons(){
    const gameType = document.querySelector("#game-type-buttons");
    gameType.classList.add("hidden");
}

function generateGameData(ships, prefix) {
    const boardSize = loadFromStorage("BoardSize");

    return {
        commander: loadFromStorage("UserName"),
        type: loadFromStorage("GameMode"),
        prefix: prefix,
        fleet: {
            rows: parseInt(boardSize.rows),
            cols: parseInt(boardSize.cols),
            ships: fetchFleetShips(ships)
        }
    };
}

function fetchFleetShips(ships) {
    const fleet = [];
    for (const ship of ships) {
        fleet.push({name: ship.name, location: getShipLocations(ship)});
    }
    return fleet;
}

function getShipLocations(ship){
    const shipLocations = [];
    for (let i = 1; i <= ship.size; i++) {
        const tile = document.querySelector(`#player-board div[class*="${ship.name}-${i}"]`);
        const row = parseInt(tile.dataset.row);
        const col = parseInt(tile.dataset.col);
        shipLocations.push(coordsToStr(row, col));
    }
    return shipLocations;
}

function coordsToStr(row, col) {
    const chars = "ABCDEFGHIJKLMNO";
    return `${chars[col - 1]}-${row}`;
}

function removeGameboardActions() {
    document.querySelector('#board-actions').remove();
    document.querySelector('#create').remove();
}

function addHistoryButton() {
    document.querySelector('#start-game').insertAdjacentHTML("afterbegin", `<button id="history-btn">History</button>`);
    document.querySelector('#history-btn').addEventListener("click", handleHistoryClick);
}
