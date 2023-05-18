"use strict";

document.addEventListener("DOMContentLoaded", () => {
    document.querySelectorAll( "#links a").forEach((link) =>{
        link.addEventListener("click", putUserNameInLocalStorage);
    });

    document.querySelector("#username").addEventListener("change", clearError);
});

const clearError = () => {
    document.querySelector("#username-error").innerHTML = "<label for='username' id='username-error'></label>";
};

function placeError(msg) {
    const $errorLabel = document.querySelector("#username-error");

    $errorLabel.innerHTML = `<label for='username' id='username-error' class="red">${msg}</label>`;
}

function putUserNameInLocalStorage(e) {
    const username = document.querySelector("input").value;
    if (!username) {
        e.preventDefault();
        placeError("Please input a valid username");
        return false;
    }

    saveToStorage("UserName", username);
    return true;
}
