// Changing input group text on focus
$(function () {
  $("input, select").on("focus", function () {
    $(this).parent().find(".input-group-text").css("border-color", "#80bdff");
  });
  $("input, select").on("blur", function () {
    $(this).parent().find(".input-group-text").css("border-color", "#ced4da");
  });
});

let pwMatch = false;
function checkPw() {
  let pw = document.getElementById("password").value.trim();
  let confirm = document.getElementById("passwordConfirmation").value.trim();

  if (pw.length == 0 && confirm.length == 0) {
    let errorP = document.querySelectorAll(".errorPassword");
    if (errorP[0].classList.contains("is-invalid")) {
      errorP[0].classList.remove("is-invalid");
    }
    if (errorP[1].classList.contains("is-invalid")) {
      errorP[1].classList.remove("is-invalid");
    }
  } else if (pw != confirm) {
    let errorP = document.querySelectorAll(".errorPassword");
    errorP[1].classList.add("is-invalid");
    errorP[1].innerHTML = "Password doesn't match!";
    pwMatch = false;
  } else {
    let errorP = document.querySelectorAll(".errorPassword");
    if (errorP[0].classList.contains("is-invalid")) {
      errorP[0].classList.remove("is-invalid");
      errorP[0].innerHTML = "";
    }
    if (errorP[1].classList.contains("is-invalid")) {
      errorP[1].classList.remove("is-invalid");
      errorP[1].innerHTML = "";
    }
    pwMatch = true;
  }
}

sessionStorage.clear();

function checkUserInput() {
  let username = document.getElementById("username").value.trim();
  let password = document.getElementById("password").value.trim();
  let confirmPW = document.getElementById("passwordConfirmation").value.trim();

  let usernameErrorMsg = "Please enter your username";
  let passwordErrorMsg = "Please enter your password";

  if (username.length == 0) {
    document.getElementById("username-invalid").classList.add("is-invalid");
    document.getElementById("username-invalid").innerHTML = usernameErrorMsg;
  } else {
    document.getElementById("username-invalid").classList.remove("is-invalid");
    document.getElementById("username-invalid").innerHTML = "";
  }

  if (password.length == 0) {
    document.getElementById("pw-invalid").classList.add("is-invalid");
    document.getElementById("pw-invalid").innerHTML = passwordErrorMsg;
  } else {
    document.getElementById("pw-invalid").classList.remove("is-invalid");
    document.getElementById("pw-invalid").innerHTML = "";
  }

  if (confirmPW.length == 0) {
    //document.getElementById("pwConfirm-invalid").classList.add("is-invalid");
    //document.getElementById("pwConfirm-invalid").innerHTML = passwordErrorMsg;
  } else {
    document.getElementById("pwConfirm-invalid").classList.remove("is-invalid");
    document.getElementById("pwConfirm-invalid").innerHTML = "";
  }

  return !document.querySelectorAll(".is-invalid").length > 0;
}

function submitForm(e) {
  if (checkUserInput) {
    var data = $("form").serialize();
    $.ajax({
      url: "/RegistrationServlet",
      type: "POST",
      data: data,

      success: function (data) {
        window.location.replace("../");
      },

      error: function (data) {
        $("#username-error").text(data.responseText);
      },

      beforeSend: function () {
        $(".sk-chase").css("display", "block");
      },

      complete: function () {
        $(".sk-chase").css("display", "none");
      },
    });
  }

  return false;
}
