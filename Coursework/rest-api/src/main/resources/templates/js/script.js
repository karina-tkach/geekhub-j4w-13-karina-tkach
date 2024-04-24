const forms = document.querySelector(".forms"),
    pwShowHide = document.querySelectorAll(".eye-icon"),
    links = document.querySelectorAll(".link");
pwShowHide.forEach(eyeIcon => {
    eyeIcon.addEventListener("click", () => {
        let pwFields = eyeIcon.parentElement.parentElement.querySelectorAll(".password, .cPassword");

        pwFields.forEach(password => {
            if (password.type === "password") {
                password.type = "text";
                eyeIcon.classList.replace("bx-hide", "bx-show");
                return;
            }
            password.type = "password";
            eyeIcon.classList.replace("bx-show", "bx-hide");
        })

    })
})

function checked() {
    return checkFirstName()
        & checkLastName()
        & checkEmail()
        & checkPassword()
        & checkPasswordConfirm();
}

function submit() {
    if (!checked()) {
        return;
    }
    let user = {
        firstName: document.getElementById("firstName").value,
        lastName: document.getElementById("lastName").value,
        password: document.getElementById("password").value,
        email: document.getElementById("email").value
    };

    let request = initRequest();

    request.open("POST", "/users/register");
    request.setRequestHeader("Accept", "application/json");
    request.setRequestHeader("Content-Type", "application/json");
    request.responseType = "json";

    request.onreadystatechange = function () {
        if (request.readyState === 4 && request.status === 200) {
            if (request.response === null) {
                document.getElementById("error_email").innerHTML = "Email already in use";
                return;
            }
            window.location.replace("/main");
        }
    }
    request.send(JSON.stringify(user));
}

function initRequest() {
    let request;
    if (window.XMLHttpRequest) {
        request = new XMLHttpRequest();
    } else {
        request = new ActiveXObject("Microsoft.XMLHTTP");
    }
    return request;
}

function resetErrorById(id) {
    document.getElementById(id).innerHTML = "";
}

function checkFirstName() {
    let regExp = /^[A-Z][a-zA-Z]*(-[a-zA-Z]+)*$/;
    let firstName = document.getElementById("firstName").value;
    if (firstName.length > 30 || firstName.length < 2 || !regExp.test(firstName)) {
        document.getElementById("error_firstName").innerHTML = "Invalid data";
        return false;
    }
    return true;
}

function checkLastName() {
    let regExp = /^[A-Z][a-zA-Z]*(-[a-zA-Z]+)*$/;
    let lastName = document.getElementById("lastName").value;
    if (lastName.length > 30 || lastName.length < 2 || !regExp.test(lastName)) {
        document.getElementById("error_lastName").innerHTML = "Invalid data";
        return false;
    }
    return true;
}

function checkEmail() {
    let regExp = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
    let email = document.getElementById("email").value;
    if (!regExp.test(email)) {
        document.getElementById("error_email").innerHTML = "Email contains forbidden symbols";
        return false;
    }
    return true;
}

function checkPassword() {
    let pass = document.getElementById("password").value;
    if (pass.length > 20 || pass.length < 8 || !validateSymbols(pass)) {
        document.getElementById("error_pass").innerHTML = "Password must be at least 8 characters long and contain at least 1 uppercase letter and 1 digit. Special characters are allowed.";
        return false;
    }
    return true;
}

function validateSymbols(str) {

    if (str.search(/\d/) === -1) {
        return false;
    } else if (str.search(/[A-Z]/) === -1) {
        return false;
    } else if (str.search(/[a-z]/) === -1) {
        return false;
    }
    return true;
}

function checkPasswordConfirm() {
    let password = document.getElementById("password").value;
    let passwordConfirm = document.getElementById("passwordConfirm").value;
    if (password !== passwordConfirm) {
        document.getElementById("error_passConfirm").innerHTML = "Passwords don't match";
        return false;
    }
    return true;
}
