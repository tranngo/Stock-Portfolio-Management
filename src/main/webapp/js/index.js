var failCounter = 0;
var firstLogin = new Date();

// Changing input group text on focus
$(function () {
  $("input, select").on("focus", function () {
    $(this).parent().find(".input-group-text").css("border-color", "#80bdff");
  });
  $("input, select").on("blur", function () {
    $(this).parent().find(".input-group-text").css("border-color", "#ced4da");
  });
});

function checkUserInput() {
  console.log("Login form was submitted!");
  let username = document.getElementById("username").value.trim();
  let password = document.getElementById("password").value.trim();

  if (username.length == 0) {
    document.getElementById("username-invalid").classList.add("is-invalid");
    document.getElementById("username-invalid").innerHTML =
      "Please enter a username";
  } else {
    document.getElementById("username-invalid").classList.remove("is-invalid");
    document.getElementById("username-invalid").innerHTML = "";
  }

  if (password.length == 0) {
    document.getElementById("pw-invalid").classList.add("is-invalid");
    document.getElementById("pw-invalid").innerHTML =
      "Please enter your password";
  } else {
    document.getElementById("pw-invalid").classList.remove("is-invalid");
    document.getElementById("pw-invalid").innerHTML = "";
  }

  if (document.querySelectorAll(".is-invalid").length > 0) {
    failCounter++;
  }

  return !document.querySelectorAll(".is-invalid").length > 0;
}

function submitForm(e) {
  console.log("login form submit");
  var data = $("form").serialize();
  console.log(data);
  if (failCounter == 3) {
    setTimeout(() => console.log("After: " + Date()), 60000);
  }
  if (checkUserInput && failCounter != 3) {
    $.ajax({
      url: "/LoginServlet",
      type: "POST",
      data: data,

      success: function (data) {
        window.location.replace("../home.html");
        failCounter = 0;
      },

      error: function (data) {
        console.log("failed login");
        $("#login-error").text("Your login info is incorrect!");
        ++failCounter;
      },
    });
  }

  return false;
}
