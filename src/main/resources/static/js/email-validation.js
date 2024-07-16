document.addEventListener("DOMContentLoaded", function() {
    const emailInput = document.getElementById("email");
    const loginForm = document.getElementById("loginForm");

    loginForm.addEventListener("submit", function(event) {
        if (!isValidEmail(emailInput.value)) {
            event.preventDefault(); // Prevent form submission
            // alert("Please enter a valid email address.");
            document.getElementById("email-error").innerText = "Please enter a valid email address."

        }
    });

    function isValidEmail(email) {
        // RegEx= matches before and after @ symbol to pattern - makes sure there is no whitespace
        var regex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        return regex.test(email);
    }
});
