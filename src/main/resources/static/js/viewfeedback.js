$(document).on('input propertychange', "textarea[name='replybox']", function () {
    var replybtn = $(this).next().next().next().next();
    if($(this).val().length > 4){
        replybtn.prop('disabled', false);
    }
    else{
        replybtn.prop('disabled', true);
    }
});

function toggle_checkbox(){
    var checkbox = document.getElementById('select_all');
    var checkboxes = document.getElementsByName('select_one')
    if(checkbox.checked){
        for(var i=0, n=checkboxes.length;i<n;i++) {
            checkboxes[i].checked = true;
            toggle_links('enable');
        }
    } else {
        for(var i=0, n=checkboxes.length;i<n;i++) {
            checkboxes[i].checked = false;
            toggle_links('disable');
        }
    }
}

function toggle_maincheckbox(){
    var checked = document.querySelectorAll('input[name="select_one"]:checked').length
    var checkbox = document.getElementById('select_all');
    if(checked == 0){
        checkbox.checked = false;
        toggle_links('disable');
    }
    else{
        toggle_links('enable');
        if (checked == document.getElementsByName('select_one').length){
            checkbox.checked = true;
        }
    }
}

function toggle_links(status){
    var links = document.getElementsByName('mlink')
    for(var i=0, n=links.length;i<n;i++) {
        if(status == 'enable'){
            links[i].classList.remove("disabled");
        }
        else{
            links[i].classList.add("disabled");
        }
    }
}

function updateRead(read){
    var feedbacks = [];
    var checkboxes = $('[name="select_one"]');
    for(var i=0, n=checkboxes.length;i<n;i++) {
        if(checkboxes[i].checked){
            var id = $(checkboxes[i]).attr('id');
            var repliedtag = document.getElementById('repliedtag' + id);
            if(!$(repliedtag).is(':visible')){
                feedbacks.push(id);
            }
        }
    }

    if(feedbacks.length > 0){
        $.ajax({
            type: "POST",
            url: "http://localhost:8083/admin/viewfeedback_update",
            data: { feedbacks: feedbacks.toString(), read: read },
            headers: {"X-CSRF-TOKEN": $("input[name='_csrf']").val()},
            success: function () {
                if(read == true){
                    for(var i=0, n=feedbacks.length;i<n;i++) {
                        var checkbox = document.getElementById(feedbacks[i]);
                        if(checkbox.checked){
                            var id = feedbacks[i];
                            var headerid = 'tags' + id;
                            var tags = document.getElementById(headerid);
                            tags.hidden = false;
                            var cardid = 'card' + id;
                            var card = document.getElementById(cardid);
                            card.style.backgroundColor = '#F5F5F5';
                        }
                    }
                }
                else{
                    for(var i=0, n=feedbacks.length;i<n;i++) {
                        var checkbox = document.getElementById(feedbacks[i]);
                        if(checkbox.checked){
                            var id = feedbacks[i];
                            var headerid = 'tags' + id;
                            var tags = document.getElementById(headerid);
                            tags.hidden = true;
                            var cardid = 'card' + id;
                            var card = document.getElementById(cardid);
                            card.style.backgroundColor = 'white';
                        }
                    }
                }
            },
            error: function (data) {
                alert("An error has occurred, please try again later.");
            }
        });
    }
}

function sendReply(id){
    var feedbackid = id.replace("btn", "");
    var textarea = document.getElementById("text" + feedbackid);
    var reply = textarea.value;
    var replybtn = document.getElementById(id);
    replybtn.disabled = true;
    var errormsg = document.getElementById('failed' + feedbackid);
    errormsg.hidden = true;

    $.ajax({
        type: "POST",
        url: "http://localhost:8083/admin/viewfeedback_reply",
        data: { feedbackid: feedbackid, reply: reply },
        headers: {"X-CSRF-TOKEN": $("input[name='_csrf']").val()},
        success: function () {
            var tags = document.getElementById('tags' + feedbackid);
            tags.hidden = false;
            var repliedtag = document.getElementById('repliedtag' + feedbackid);
            repliedtag.hidden = false;
            var cardid = 'card' + feedbackid;
            var card = document.getElementById(cardid);
            card.style.backgroundColor = '#F5F5F5';
            textarea.disabled = true;
            var openbtn = document.getElementById('opener' + feedbackid);
            openbtn.value = "View Reply";
            var successmsg = document.getElementById('success' + feedbackid);
            successmsg.hidden = false;
        },
        error: function (data) {
            replybtn.disabled = false;
            errormsg.hidden = false;
        }
    });
}