function toggle_next(){
    var form = document.getElementById('mainform')
    if (!form.checkValidity()) {
        form.reportValidity()
        return;
    }
    var nextbtn = document.getElementById('nextbtn')
    var editbtn = document.getElementById('editbtn')
    var submitbtn = document.getElementById('submitbtn')
    var confirmmsg = document.getElementById('confirmmsg')

    nextbtn.hidden = true
    editbtn.hidden = false
    submitbtn.hidden = false
    confirmmsg.hidden = false

    var fullName = document.getElementById('fullName')
    fullName.readOnly = true
    var email = document.getElementById('email')
    email.readOnly = true
}

function toggle_edit(){
    var nextbtn = document.getElementById('nextbtn')
    var editbtn = document.getElementById('editbtn')
    var submitbtn = document.getElementById('submitbtn')
    var confirmmsg = document.getElementById('confirmmsg')

    nextbtn.hidden = false
    editbtn.hidden = true
    submitbtn.hidden = true
    confirmmsg.hidden = true

    var fullName = document.getElementById('fullName')
    fullName.readOnly = false
    var email = document.getElementById('email')
    email.readOnly = false
}