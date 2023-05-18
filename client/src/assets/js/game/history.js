"use strict";

function handleHistoryClick(e) {
    const $history = document.querySelector("#history");
    if ($history.classList.contains('hidden')) {
        renderHistory();
    }
    $history.classList.toggle('hidden');
}

function renderHistory(){
    fetchFromServer(`/games/${_token.gameId}`).then(gameData => {
        let html = ``;
        for (const turn of gameData.history) {
            html += `
                <div class="history-turn">
                    <p>${turn.attackingCommander}</p>
                    <ul>
                        ${generateResultList(turn.result)}
                    </ul>            
                </div>`;
        }
        if (html === ``) {
            html += `<div class="history-turn"><p>No turns taken yet</p></div>`;
        }
        document.querySelector("#history").innerHTML = `<h2>History</h2><div id="close-btn">&times;</div>` + html;
        document.querySelector("#close-btn").addEventListener("click", handleHistoryClick);
    }).catch(err => {
        document.querySelector("#history").innerHTML = `<h2>History</h2><div id="close-btn">&times;</div><div class="history-turn"><p>No turns taken yet</p></div>`;
        document.querySelector("#close-btn").addEventListener("click", handleHistoryClick);
    });
}

function generateResultList(result){
    if (typeof result === "string") {
        return `<li><strong>${result}</strong></li>`;
    } else if (result.ship) {
        return `<li><strong>Moved:</strong> ${result.ship.name}</li>`;
    } else {
        return generateListSalvo(result);
    }
}

function generateListSalvo(result) {
    let list = ``;
    for (const resultKey in result) {
        if (result.hasOwnProperty(resultKey)) {
            let value = result[resultKey];
            if (value) {
                value = "Hit!";
            } else {
                value = "Shot missed";
            }
            list += `<li><strong>${resultKey}:</strong> ${value}</li>`;
        }
    }
    return list;
}
