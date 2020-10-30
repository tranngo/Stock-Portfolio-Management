$(".logout-btn").on("click", function () {
  console.log("clicked!");
  $.ajax({
    url: "LogoutServlet",
    type: "POST",
  });
});
