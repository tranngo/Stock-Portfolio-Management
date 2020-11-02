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

  return !document.querySelectorAll(".is-invalid").length > 0;
}

function submitForm(e) {
  // e.preventDefault();
  if (checkUserInput) {
    var data = $("form").serialize();
    $.ajax({
      url: "/LoginServlet",
      type: "POST",
      data: data,

      success: function (data) {
        window.location.replace("../home.html");
      },

      error: function (data) {
        // console.log("failed");
        // console.log(data.responseText);
        $("#login-error").text("This login combination is invalid!");
      },
    });
  }

  return false;
}

$(document).ajaxStart(function () {
  $(".sk-chase").css("display", "block");
});

$(document).ajaxComplete(function () {
  $(".sk-chase").css("display", "none");
});
