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
  let fname = document.getElementById("firstName").value.trim();
  let lname = document.getElementById("lastName").value.trim();
  let email = document.getElementById("email").value.trim();
  sessionStorage.setItem("email", email);
  let location = document.getElementById("location").value.trim();
  let phone = document.getElementById("phoneNumber").value.trim();
  let password = document.getElementById("password").value.trim();
  let confirmPW = document.getElementById("passwordConfirmation").value.trim();

  if (fname.length == 0)
  {
    document.getElementById("fname-invalid").classList.add("is-invalid");
    document.getElementById("fname-invalid").innerHTML = "Please enter first name";
  }
  else
  {
    document.getElementById("fname-invalid").classList.remove("is-invalid");
    document.getElementById("fname-invalid").innerHTML = "";
  }

  if (lname.length == 0)
  {
    document.getElementById("lname-invalid").classList.add("is-invalid");
    document.getElementById("lname-invalid").innerHTML = "Please enter last name";
  }
  else
  {
    document.getElementById("lname-invalid").classList.remove("is-invalid");
    document.getElementById("lname-invalid").innerHTML = "";
  }

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

  if (email == "taken@usc.edu")
  {
    document.querySelector(".hiddenDiv").classList.add("is-invalid");
    document.querySelector(".hiddenDiv").classList.remove("hidden");
    document.querySelector(".hiddenTxt").innerHTML = "This email has been registered";
  }
  else
  {
    document.querySelector(".hiddenDiv").classList.remove("is-invalid");
    document.querySelector(".hiddenDiv").classList.add("hidden");
    document.querySelector(".hiddenTxt").innerHTML = "";
  }

  if (location.length == 0)
  {
    document.getElementById("location-invalid").classList.add("is-invalid");
    document.getElementById("location-invalid").innerHTML = "Please enter your location";
  }
  else
  {
    document.getElementById("location-invalid").classList.remove("is-invalid");
    document.getElementById("location-invalid").innerHTML = "";
  }

  if (phone.length == 0)
  {
    document.getElementById("phone-invalid").classList.add("is-invalid");
    document.getElementById("phone-invalid").innerHTML = "Please enter your phone number";
  }
  else
  {
    document.getElementById("phone-invalid").classList.remove("is-invalid");
    document.getElementById("phone-invalid").innerHTML = "";
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