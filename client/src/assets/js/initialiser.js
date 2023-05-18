"use strict";
let _token = null;

document.addEventListener('DOMContentLoaded',init);

function init(){
    testConnection();
}


function testConnection(){
    fetchFromServer('/ships', 'GET').catch(errorHandler);
}
