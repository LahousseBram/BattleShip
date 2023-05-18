'use strict';

function checkForOtherPlayer(gameId) {
    fetchFromServer(`/games/${gameId}`, 'GET').then(response => {
        if (response.started) {
            displayUsers();
            checkForTurn(gameId);
            removeGameTypeButtons();
        } else {
            setTimeout(() => {
                checkForOtherPlayer(gameId);
                }, _config.delay);
        }
    }).catch(error => errorHandler(error));
}

function checkForTurn(gameId) {
    fetchFromServer(`/games/${gameId}`, 'GET').then(response => {
        if (response.winner) {
            renderWinner(response.winner);
        } else if (response.attackingCommander === loadFromStorage("UserName")) {
            renderEnemyAttack(response.history);
            enableTurnTypeSelectors();
            takeTurn();
        } else {
            disableTurnTypeSelectors();
            setOverlay(false);
            setTimeout(() => {
                checkForTurn(gameId);
                }, _config.delay);
        }
        renderSunkenShips(response.sunkShips);
    }).catch(error => errorHandler(error));
}

function takeTurn() {
    const turnType = loadFromStorage("turn-type");
    if (turnType === 'move') {
        setOverlay(false);
        document.querySelectorAll('#enemy-board .board-tile:not(.missed-missile):not(.ship-hit)').forEach(el => el.removeEventListener('click', handleFireMissile));
        document.querySelectorAll('#player-board .board-tile:not(.empty)').forEach(el => el.addEventListener('click', handleMove));
    } else {
        setOverlay(true);
        document.querySelectorAll('#player-board .board-tile:not(.empty)').forEach(el => el.removeEventListener('click', handleMove));
        document.querySelectorAll('#enemy-board .board-tile:not(.missed-missile):not(.ship-hit)').forEach(el => el.addEventListener('click', handleFireMissile));
    }

}

function renderWinner(winner) {
    if (winner === loadFromStorage("UserName")) {
        setOverlay(true);
    } else {
        setOverlay(false);
    }
    document.querySelector('body').insertAdjacentHTML('afterend',
        `<div class="overlay winner">
            <h2>${winner} won the game!</h2>
            <div id="winner-container">
                <a href="../">Main Menu</a>
                <a href="./">Play Again</a>
            </div>
        </div>`);
}

function renderEnemyAttack(history) {
    for (const attack of history) {
        if (attack.attackingCommander !== loadFromStorage('UserName') && !attack.result.ship && typeof attack.result !== 'string') {
            renderSalvo(attack.result, 'player-board');
        }
    }
}

function setOverlay(enemyBoard) {
    const boardName = enemyBoard ? 'enemy-board' : 'player-board';
    if (document.querySelector(`#${boardName} .overlay.hidden`) === null) {
        document.querySelectorAll('.overlay').forEach(el => el.classList.toggle('hidden'));
    }
}
