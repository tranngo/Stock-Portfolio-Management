google.charts.load("current", { packages: ["corechart"] });
  google.charts.setOnLoadCallback(drawMainChart);
  google.charts.setOnLoadCallback(drawStockHistoryChart);

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
  
  function drawStockHistoryChart() {
	    var data = google.visualization.arrayToDataTable(jsonArray);

	    var options = {
	      title: "Company Performance",
	      curveType: "function",
	      legend: { position: "bottom" },
	    };

	    var chart = new google.visualization.LineChart(
	      document.getElementById("stock-history-chart")
	    );

	    chart.draw(data, options);

	    // Used to make chart responsive
	    $(document).ready(function () {
	      $(window).resize(function () {
	        draStockHistoryChart();
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
 

$("#add-stock-button").on("click", function() {
	$(".modal-title").html("Add Stock Transaction");
	$("#add-modal-content").attr("class", "display-block");
	$("#upload-file-modal-content").attr("class", "display-none");
	
	$("#mainModal").modal({
		backdrop: true,
		keyboard: true,
		focus: true,
		show: true
	});
});

$("#upload-file-button").on("click", function() {
	$(".modal-title").html("Upload File");
	$("#modal-add-sell-button").html("Upload");
	$("#add-modal-content").attr("class", "display-none");
	$("#upload-file-modal-content").attr("class", "display-block");
	
	$("#mainModal").modal({
		backdrop: true,
		keyboard: true,
		focus: true,
		show: true
	});
});

$(".toggle-button").on("click", function() {
	if ($(this).attr("class").includes("fa-toggle-on")) {
		$(this).attr("class", $(this).attr("class").replace("fa-toggle-on", "fa-toggle-off"));
	} else {
		$(this).attr("class", $(this).attr("class").replace("fa-toggle-off", "fa-toggle-on"));
	}
});

$("#sp-button").on("click", function() {
	if ($(this).attr("class").includes("btn-primary")) {
		$(this).attr("class", $(this).attr("class").replace("btn-primary", "btn-secondary"));
	} else {
		$(this).attr("class", $(this).attr("class").replace("btn-secondary", "btn-primary"));
	}
})