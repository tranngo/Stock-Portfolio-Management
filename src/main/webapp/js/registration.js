// Changing input group text on focus
$(function () {
    $('input, select').on('focus', function () {
        $(this).parent().find('.input-group-text').css('border-color', '#80bdff');
    });
    $('input, select').on('blur', function () {
        $(this).parent().find('.input-group-text').css('border-color', '#ced4da');
    });
});

let pwMatch = false;
function checkPw()
{
  let pw = document.getElementById("password").value.trim();
  let confirm = document.getElementById("passwordConfirmation").value.trim();

  if (pw.length == 0 && confirm.length == 0)
  {
    let errorP = document.querySelectorAll(".errorPassword");
    if (errorP[0].classList.contains("is-invalid"))
    {
      errorP[0].classList.remove("is-invalid");
    }
    if (errorP[1].classList.contains("is-invalid"))
    {
      errorP[1].classList.remove("is-invalid");
    }
  }
  else if (pw != confirm)
  {
    let errorP = document.querySelectorAll(".errorPassword");
    errorP[0].classList.add("is-invalid");
    errorP[1].classList.add("is-invalid");
    errorP[0].innerHTML = "Password doesn't match!";
    errorP[1].innerHTML = "Password doesn't match!";
    pwMatch = false;
  }
  else
  {
    let errorP = document.querySelectorAll(".errorPassword");
    if (errorP[0].classList.contains("is-invalid"))
    {
      errorP[0].classList.remove("is-invalid");
      errorP[0].innerHTML = "";
    }
    if (errorP[1].classList.contains("is-invalid"))
    {
      errorP[1].classList.remove("is-invalid");
      errorP[1].innerHTML = "";
    }
    pwMatch = true;
  }
}

sessionStorage.clear();

// Check if all entries are filled
document.querySelector('form').onsubmit = function()
{
  // event.preventDefault();
  let username = document.getElementById("username").value.trim();
  /*sessionStorage.setItem("email", email);*/
  let password = document.getElementById("password").value.trim();
  let confirmPW = document.getElementById("passwordConfirmation").value.trim();

  if (username.length == 0)
  {
    document.getElementById("username-invalid").classList.add("is-invalid");
    document.getElementById("username-invalid").innerHTML = "Please enter a username";
  }
  else
  {
    document.getElementById("username-invalid").classList.remove("is-invalid");
    document.getElementById("username-invalid").innerHTML = "";
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

  if (confirmPW.length == 0)
  {
    document.getElementById("pwConfirm-invalid").classList.add("is-invalid");
    document.getElementById("pwConfirm-invalid").innerHTML = "Please enter your password";
  }
  else
  {
    document.getElementById("pwConfirm-invalid").classList.remove("is-invalid");
    document.getElementById("pwConfirm-invalid").innerHTML = "";
  }
  
  
  return (!document.querySelectorAll('.is-invalid').length > 0 );
}