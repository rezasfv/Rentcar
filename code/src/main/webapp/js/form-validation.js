/*
    Author: Francesco Chemello
    Version: 1.0
    Since: 1.0

    Form validation starting from Bootstrap 5
*/

document.addEventListener('DOMContentLoaded', function() {
    console.log("Form-Validation script loaded.");
    var form = document.querySelector('form');
    form.addEventListener('submit', function(event) {
        console.log("Form validation started...");
        var isValid = validateForm(); // Check the form validity
        if (!isValid) {
            console.log("Form is not valid!");
            event.preventDefault(); // Prevent the form submission
        } else {
            console.log("Form is valid!");
            // The form is valid, load and execute the script
            loadAndExecuteScript("https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js");
        }
    });
});

function validateForm() {
    'use strict'

    console.log('Validating the form...');

    var forms = document.querySelectorAll('.needs-validation');
    var isValid = true; // Assume the form is valid

    // Regular expressions
    var emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]{2,}$/;
    var passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[#@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/; // Minimum eight characters, at least one uppercase letter, one lowercase letter, one number and one special character
    var licenseRegex = /^[A-Za-z\d]{10}$/; // 10 characters, only letters and numbers

    Array.from(forms).forEach(function (form) {
        Array.from(form.elements).forEach(function (element) {
            
            if (element.type === "email") {

                if (!emailRegex.test(element.value)) {
                    console.log("Invalid email format for element: " + element.name);
                    element.classList.add('is-invalid');
                    element.classList.remove('is-valid');
                    isValid = false; // The email is not valid
                } else {
                    element.classList.add('is-valid');
                    element.classList.remove('is-invalid');
                }
            } else if (element.type === "password") { 

                if (!passwordRegex.test(element.value)) {
                    console.log("Password does not meet security requirements for element: " + element.name);
                    element.classList.add('is-invalid');
                    element.classList.remove('is-valid');
                    isValid = false; // The password is not secure
                } else {
                    element.classList.add('is-valid');
                    element.classList.remove('is-invalid');
                }
            } else if (element.type === "date" && element.id !== "dateOfBirth") {
                var currentDate = new Date();
                var inputDate = new Date(element.value);

                // Verify if the date is in the past
                if (inputDate < currentDate || isNaN(inputDate.getTime())) {
                    console.log("Date cannot be in the past for element: " + element.name);
                    element.classList.add('is-invalid');
                    element.classList.remove('is-valid');
                    isValid = false;
                } else {
                    element.classList.add('is-valid');
                    element.classList.remove('is-invalid');
                }
            } else if (element.type === "date" && element.id === "dateOfBirth") {
                var currentDate = new Date();
                var adjustedDate = new Date(currentDate.setFullYear(currentDate.getFullYear() - 18)); // Subtract 18 years
                var inputDate = new Date(element.value);
                
                // Verify if the person is at least 18 years old
                if (inputDate > adjustedDate || isNaN(inputDate.getTime())) { 
                    console.log("User is a minor: " + element.name);
                    element.classList.add('is-invalid');
                    element.classList.remove('is-valid');
                    isValid = false;
                } else {
                    element.classList.add('is-valid');
                    element.classList.remove('is-invalid');
                }
            } else if (element.id === "licenseNumber") {

                if(!licenseRegex.test(element.value)) {
                    console.log("Invalid license format for element: " + element.name);
                    element.classList.add('is-invalid');
                    element.classList.remove('is-valid');
                    isValid = false; // The email is not valid
                } else {
                    element.classList.add('is-valid');
                    element.classList.remove('is-invalid');
                }
            } else {
                if (!element.checkValidity()) {
                    element.classList.add('is-invalid');
                    element.classList.remove('is-valid');
                    isValid = false; // False if at least one field is invalid
                } else {
                    element.classList.add('is-valid');
                    element.classList.remove('is-invalid');
                }
            }
        });

        // form.classList.add('was-validated');
    });

    return isValid; // Return true if the form is valid, false otherwise
}

function loadAndExecuteScript(scriptPath) {
    var script = document.createElement('script');
    script.src = scriptPath;
    document.head.appendChild(script);

    script.onload = function() {
        console.log('Script loaded and executed.');
    };
}