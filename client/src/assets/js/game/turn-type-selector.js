'use strict';

function renderTurnTypeSelectors() {
    document.querySelector('#ships').innerHTML = `
        <ul>
            <li class="turn-type" id="simple">Regular</li>
            <li class="turn-type" id="salvo">Salvo</li>
            <li class="turn-type" id="move">Move</li>
        </ul>
    `;
}

function enableTurnTypeSelectors() {
    if (loadFromStorage('GameMode') === "simple") {
        document.querySelector('#simple').classList.add('selectable', 'selected');
    } else if (loadFromStorage('GameMode') === "salvo") {
        document.querySelector('#salvo').classList.add('selectable', 'selected');
    } else {
        document.querySelector('#simple').classList.add('selectable', 'selected');
        document.querySelector('#move').classList.add('selectable');
        document.querySelector('#move').classList.remove('selected');
    }
    addSelectTurnTypeEventListeners();
}

function disableTurnTypeSelectors() {
    document.querySelectorAll('.turn-type.selectable').forEach(el => {
        el.removeEventListener('click', selectTurnType);
        el.classList.remove("selectable");
    });
}

function addSelectTurnTypeEventListeners() {
    saveToStorage("turn-type", "simple");
    document.querySelectorAll('.turn-type.selectable').forEach(el => el.addEventListener('click', selectTurnType));
}

function selectTurnType(e) {
    const currentSelection = document.querySelector(".selected");

    if (currentSelection && currentSelection !== e.target) {
        currentSelection.classList.toggle("selected");
        e.target.classList.toggle("selected");
        saveToStorage("turn-type", e.target.id);
        takeTurn();
    }
}
