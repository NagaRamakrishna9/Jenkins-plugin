// script.js
console.log("Hello from script.js");

document.addEventListener("DOMContentLoaded", function() {
    // Get the select element
    var selectElement = document.getElementsByClassName("browser")[0];
    // Get the input field
    var inputField = document.getElementsByClassName("custominput");

    // Add event listener to the select element
    selectElement.addEventListener("change", function() {

            // Enable the input field
            inputField.style.display = "block";

    });
});

