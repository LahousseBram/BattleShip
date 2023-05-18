"use strict";

function displayUsers(){
    const usernamePlayer = loadFromStorage("UserName");
    document.querySelector("#player-board h2").innerHTML = usernamePlayer ? usernamePlayer : "Player";
    const $opponent = document.querySelector("#enemy-board h2");

    if (!_token) {
        $opponent.innerHTML = "Enemy";
        return;
    }
    const gameId = _token.gameId;

    fetchFromServer(`/games/${gameId}`).then(gameData => {
        $opponent.innerHTML = gameData.defendingCommander === usernamePlayer ? gameData.attackingCommander : gameData.defendingCommander;
        saveToStorage("EnemyName", gameData.defendingCommander === usernamePlayer ? gameData.attackingCommander : gameData.defendingCommander);
    }).catch(err => errorHandler(err));
}

