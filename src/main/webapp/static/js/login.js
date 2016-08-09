$(document).ready(function ($) {
    $('#loginform').submit(function (event) {
        event.preventDefault();
        var data = 'username=' + $('#username').val() + '&password=' + $('#password').val();
        $.ajax({
            data: data,
            timeout: 1000,
            type: 'POST',
            url: '/login'
        }).done(function(data, textStatus, jqXHR) {
        	alert(textStatus);
        	alert(data);
        	alert(jqXHR);
            //var preLoginInfo = JSON.parse($.cookie('dashboard.pre.login.request'));
            //window.location = preLoginInfo.url;
        	alert('login ok');
 
        }).fail(function(jqXHR, textStatus, errorThrown) {
            alert('Booh! Wrong credentials, try again!');
        });
    });
});