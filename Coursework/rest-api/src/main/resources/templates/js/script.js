const forms = document.querySelector(".forms"),
    pwShowHide = document.querySelectorAll(".eye-icon"),
    links = document.querySelectorAll(".link");
pwShowHide.forEach(eyeIcon => {
    eyeIcon.addEventListener("click", () => {
        let pwFields = eyeIcon.parentElement.parentElement.querySelectorAll(".password, .cPassword");

        pwFields.forEach(password => {
            if(password.type === "password"){
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
    let token = document.querySelector('meta[name="_csrf"]').content;
    let header = document.querySelector('meta[name="_csrf_header"]').content;

    let request = initRequest();

    request.open("POST", "/users/register");
    request.setRequestHeader(header, token);
    request.setRequestHeader("Accept", "application/json");
    request.setRequestHeader("Content-Type", "application/json");
    request.responseType = "json";

    request.onreadystatechange = function () {
        if (request.readyState === 4 && request.status === 200) {
            if (request.response === null) {
                document.getElementById("error_email").innerHTML = "Email already in use";
                return;
            }
            alert("Register successful!");
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
    let regExp = /^([A-Za-z-]){2,100}$/;
    let firstName = document.getElementById("firstName").value;
    if (!regExp.test(firstName)) {
        document.getElementById("error_firstName").innerHTML = "Invalid data";
        return false;
    }
    return true;
}

function checkLastName() {
    let regExp = /^([A-Za-z-]){2,100}$/;
    let lastName = document.getElementById("lastName").value;
    if (!regExp.test(lastName)) {
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
    let regExp = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[A-Za-z\d@$!%*?&]{8,}$/;
    let pass = document.getElementById("password").value;
    if (!regExp.test(pass)) {
        document.getElementById("error_pass").innerHTML = "Password must be at least 8 character long and contain at least 1 uppercase letter and 1 digit.";
        return false;
    }
    return true;
}

function checkPasswordConfirm() {
    let password = document.getElementById("password").value;
    let passwordConfirm = document.getElementById("passwordConfirm").value;
    if (password !== passwordConfirm) {
        document.getElementById("error_passConfirm").innerHTML = "Password don't match";
        return false;
    }
    return true;
}
