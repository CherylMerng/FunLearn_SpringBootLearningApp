jQuery(document).ready(function(){
    $('input:radio').change(function(){
        var checkedCount = $('input:radio:checked').length;
        var percent = checkedCount/48*100
        $('#progressbar').css('width', percent + '%');
        if(percent == 50){
            $('#progressbar').css({ 'background': 'Orange' });
        }
        else if(percent == 100){
            $('#errortext').text('');
            $('#progressbar').css({ 'background': '#28f79a' });
        }
        $('#progresstext').text(checkedCount + '/48 answered');
    });
});

function check(){
    if($('input:radio:checked').length == 48){
        return true;
    }

    $('#errortext').text("Please ensure all questions are rated before submitting.");
    return false
}

function next(){
    var topbox = document.getElementById('topbox');
    topbox.hidden = false;
}

function prev(){
    var topbox = document.getElementById('topbox');
    topbox.hidden = true;
}