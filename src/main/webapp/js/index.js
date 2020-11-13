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


var timerFinished = true;
var locked = false;
var failedAttempts = 0;
var firstInSeries = new Date("2011-04-20T09:30:51.01"); //default

function startTimer() {
	timerFinished = false;
}

function stopTimer() {
	timerFinished = true;
	locked = false;
	failedAttempts = 0;
	lockoutErrorFlag = false;
	$(".login-btn").prop('disabled', false);
}

function submitForm(e) {


  if (checkUserInput) {
    var data = $("form").serialize();
    $.ajax({
      url: "/LoginServlet",
      type: "POST",
      data: data,

      success: function (data) {
      	failedAttempts = 0;
      	locked = false;
        window.location.replace("../home.html");
      },

      error: function (data) {
     	//We only want 3 failed attempts within 1 min to count
     	var currentTime = new Date(); // current time
     	var temp1 = (firstInSeries.getHours()*60*60) + (firstInSeries.getMinutes()*60) + firstInSeries.getSeconds();
     	var temp2 = (currentTime.getHours()*60*60) + (currentTime.getMinutes()*60) + currentTime.getSeconds();
     	
     	var validFailedAttempt = true;
     	if(failedAttempts == 0) {
     		validFailedAttempt = true;
     		failedAttempts = 0;
     		firstInSeries = currentTime;
     	}
     	else {
     		var closeEnough = ((temp2-temp1) < 60);
     		if(closeEnough == true) {
     			validFailedAttempt = true;
     		}
     		else {
     			validFailedAttempt = false;
     			failedAttempts = 1;
     			firstInSeries = currentTime;
     		}
     	}
     	
      	if(validFailedAttempt) {
      		failedAttempts = failedAttempts + 1;
      	}
      	if(failedAttempts == 3) {
      		locked = true;
      		failedAttempts = 0;
      		var lockoutErrorFlag = true;
      		startTimer();
      		window.setTimeout(stopTimer, 60000);
      	}
      	else {
      		lockoutErrorFlag = false;
      	}
      	
      	if(lockoutErrorFlag == true) {
      		$("#login-error").text("Too many failed login attempts, locked for 1 min");
      		$(".login-btn").prop('disabled', true);
      	}
        else {
        	$("#login-error").text("This login combination is invalid!");
        }
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
