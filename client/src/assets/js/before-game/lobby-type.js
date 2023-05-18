"use strict";

document.addEventListener("DOMContentLoaded", ()  => {
    const lobbyTypeElement = document.querySelector("#lobby-type");
    lobbyTypeElement.addEventListener("click", toggleLobbyType);
});

function toggleLobbyType(event) {
    event.preventDefault();
    const lobbyTypeElement = document.querySelector("#lobby-type");
    // toggle button between public <--> private
    if (lobbyTypeElement.textContent === "private") {
        setLobbyType("public", lobbyTypeElement);
    } else {
        setLobbyType("private", lobbyTypeElement);
    }
}

function setLobbyType(lobbyType, lobbyTypeElement) {
    lobbyTypeElement.textContent = lobbyType; // found this on the internet and thought it was a better fit for this case instead of innerHTML
    lobbyTypeElement.className = lobbyType;
}
