// Changing input group text on focus
$(function () {
    $('input, select').on('focus', function () {
        $(this).parent().find('.input-group-text').css('border-color', '#80bdff');
    });
    $('input, select').on('blur', function () {
        $(this).parent().find('.input-group-text').css('border-color', '#ced4da');
    });
});

document.querySelector('form').onsubmit = function()
{
    let email = document.getElementById("email").value.trim();
    let password = document.getElementById("password").value.trim();

    if (email.length == 0)
    {
        document.getElementById("email-invalid").classList.add("is-invalid");
        document.getElementById("email-invalid").innerHTML = "Please enter an email";
    }
    else
    {
        document.getElementById("email-invalid").classList.remove("is-invalid");
        document.getElementById("email-invalid").innerHTML = "";
    }

    if (password.length == 0)
    {
        document.getElementById("pw-invalid").classList.add("is-invalid");
        document.getElementById("pw-invalid").innerHTML = "Please enter your password";
    }
    else
    {
        document.getElementById("pw-invalid").classList.remove("is-invalid");
        document.getElementById("pw-invalid").innerHTML = "";
    }

    return (!document.querySelectorAll('.is-invalid').length > 0 );
}