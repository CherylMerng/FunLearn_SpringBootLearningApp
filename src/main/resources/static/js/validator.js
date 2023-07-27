function checkPswdMain(){
    var formatError = document.getElementById('formatError')
    var backendErrors = document.getElementsByName('backendError')
    if(backendErrors != null){
        for (let i = 0; i < backendErrors.length; i++) {
            backendErrors[i].hidden = true;
        }
    }

    if(checkPswdFormat()){
        formatError.hidden = true;
    }
    else{
        formatError.hidden = false;
    }
    var matchError = document.getElementById('matchError')
    if(checkPswd()){
        matchError.hidden = true;
    }
    else{
        matchError.hidden = false;
    }
}

function checkPswd(){
    var passwordbox = document.getElementById('inputPassword')
    var confirmpassword = document.getElementById('confirmPassword')
    var password = passwordbox.value
    var password2 = confirmpassword.value
    if(password === password2){
        return true;
    }
    return false;
}

function checkPswdFormat(){
    var passwordbox = document.getElementById('inputPassword')
    var password = passwordbox.value
    var format = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[^a-zA-Z0-9])(?!.*\s).{8,15}$/;
    if(password.match(format)){
        return true;
    }
    return false;
}

function checkPswdFormat0(){
    var passwordbox = document.getElementById('currentPassword')
    var password = passwordbox.value
    var format = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[^a-zA-Z0-9])(?!.*\s).{8,15}$/;
    if(password.match(format)){
        return true
    }
    return false
}

function checkPswdExtra(){
    var backendErrors = document.getElementsByName('backendError')
    for (let i = 0; i < backendErrors.length; i++) {
        backendErrors[i].hidden = true;
    }
    var formatError = document.getElementById('formatError0')
    if(checkPswdFormat0()){
        formatError.hidden = true;
    }
    else{
        formatError.hidden = false;
    }
}

function check(){
    var passwordbox = document.getElementById('currentPassword')
    if(passwordbox != null){
        return checkPswdFormat0()
    }

    if(checkPswd() && checkPswdFormat()){
        return true;
    }
    return false;
}