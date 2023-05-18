"use strict";

document.addEventListener("DOMContentLoaded", () => {
    document.querySelector("body").addEventListener("keypress", handleRotate);

    saveToStorage("CurrentRotation", 0); // initialize value of the current rotation to 0
});

function handleRotate(e) {
    if (e.key !== 'r' || loadFromStorage("selected-ship") === {}) {
        return false;
    } // check if they have a ship selected

    let currentRotation = loadFromStorage("CurrentRotation");
    if (!currentRotation) {
        currentRotation = 0;
    }

    previewPlacement(document.querySelector(".board-tile:hover"));

    saveToStorage("CurrentRotation", currentRotation + 1);

    previewPlacement(document.querySelector(".board-tile:hover"));
    return true;
}
