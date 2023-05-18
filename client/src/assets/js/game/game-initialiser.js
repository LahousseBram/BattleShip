"use strict";

document.addEventListener('DOMContentLoaded',init);

function init(){
    document.querySelector('#player-board .gameboard').innerHTML = generateBoard(false);
    document.querySelector('#enemy-board .gameboard').innerHTML = generateBoard(true);
    document.querySelector("#create").addEventListener('click', displayGameType);
    document.querySelector("#public-game").addEventListener("click", startPublicGame);
    document.querySelector("#private-game").addEventListener("click",startPrivateGame);
    document.querySelector("#join-game").addEventListener("click", startJoinGame);



    fetchFromServer('/ships', 'GET').then(response => renderSelectShips(response.ships)).catch(errorHandler);

    document.querySelector('#random').addEventListener('click', handlePlaceRandomShips);
    document.querySelector("#clear").addEventListener("click", handleClearBoard);

    displayUsers();
}

function displayGameType (e){
    e.preventDefault();
    removeGameboardActions();
    const $gameType = document.querySelector("#game-type-buttons");
    $gameType.classList.remove("hidden");
    $gameType.classList.add("game-type");
}
