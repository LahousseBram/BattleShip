'use strict';

function displaySunkenShips(sunkenShips, id){
    let html = ``;
    for (const sunkenShip of sunkenShips){
        html += `<img class="sunken-ship" src="../assets/img/ships/${sunkenShip.name}.png" alt="${sunkenShip.name}">`;
    }
    document.querySelector(`#${id}`).innerHTML = html;
}

function renderSunkenShips(sunkShips) {
    displaySunkenShips(sunkShips[loadFromStorage("UserName")],"sunkenShipsPlayer");
    displaySunkenShips(sunkShips[loadFromStorage("EnemyName")],"sunkenShipsEnemy");
}




