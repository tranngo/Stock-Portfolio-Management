google.charts.load("current", { packages: ["corechart"] });
google.charts.setOnLoadCallback(drawMainChart);

var jsonArray = [
  ["Year", "Sales", "Expenses"],
  ["2004", 1000, 400],
  ["2005", 1170, 460],
  ["2006", 660, 1120],
  ["2007", 1030, 540],
];

function drawMainChart() {
  var data = google.visualization.arrayToDataTable(jsonArray);

  var options = {
    title: "Company Performance",
    curveType: "function",
    legend: { position: "bottom" },
  };

  var chart = new google.visualization.LineChart(
    document.getElementById("main-chart")
  );

  chart.draw(data, options);

  // Used to make chart responsive
  $(document).ready(function () {
    $(window).resize(function () {
      drawMainChart();
    });
  });
}

function submitForm(e) {
  // e.preventDefault();
  $.ajax({
    url: "GraphServlet",
    type: "GET",

    success: function (result) {
      jsonArray = eval(result);
      console.log(jsonArray);

      for (let i = 1; i < jsonArray.length; ++i) {
        for (let j = 1; j < jsonArray[i].length; ++j) {
          if (jsonArray[i][j] === "null") {
            jsonArray[i][j] = null;
          } else {
            jsonArray[i][j] = parseInt(jsonArray[i][j]);
          }
        }
      }

      drawMainChart();
    },
  });
  return false;
}
