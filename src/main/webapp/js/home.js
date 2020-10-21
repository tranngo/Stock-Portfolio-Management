google.charts.load("current", { packages: ["corechart"] });
  google.charts.setOnLoadCallback(drawMainChart);

  var jsonArray = [
    ["Year", "Sales", "Expenses"],
    ["2004", 1000, 400],
    ["2005", 1170, 460],
    ["2006", 660, 1120],
    ["2007", 1030, 540],
  ];
  
  
  //Explanation: the graph has a bunch of things going on. There's toggles that are on,
  //toggles that are off. A certain start/end date that might have been selected. Maybe
  //a few external stock lines that we need to display.
  //At this point, let's say the user turns on the toggle for "NTNX", we want to send a
  //request to GraphServlet saying "gimme all the stuff I had before and also take into
  //account this NTNX toggle". The state of the graph which is tracked by the variables
  //below is the "all the stuff I had before" piece. When "NTNX" is toggled on, you
  //add it to the state_portfolioContributors array and then call refreshGraph().
  //refreshGraph() will take the whole current state and magically fetch the right
  //data for the graph.
  
  
  
  
  //These are the state variables which affect the graph, setting default values
  state_portfolioContributors = [];
  state_externalStocks = ["NTNX", "JNJ", "FB", "TSLA"];
  state_start = "2020-01-01";
  state_end = "2020-06-01";
  
  //Calling this function will take the "state" and pass it to GraphServlet as your request
  function refreshGraph() {

    console.log("Graph refresh requested");
    console.log("State variable -> Start date: " + state_start);
    console.log("State variable -> End date: " + state_end);
    
    state_portfolioContributors_asAString = "";
    state_externalStocks_asAString = "";
    
    //Turn portfolio contributors from an array into a string
    var i = 0;
	for (i = 0; i < state_portfolioContributors.length; i++) { 
	  state_portfolioContributors_asAString += state_portfolioContributors[i] + ",";
	}
    
    //Turn external stocks from an array into a string
    var j = 0;
	for (j = 0; j < state_externalStocks.length; j++) { 
	  state_externalStocks_asAString += state_externalStocks[j] + ",";
	}
    
    console.log("State variable (but as a string) -> Portfolio contributors: " + state_portfolioContributors_asAString);
    console.log("State variable (but as a string) -> External stocks: " + state_externalStocks_asAString);
    
    //Send the state as part of the request to GraphServlet
    $.ajax({
      url: "GraphServlet",
      type: "GET",
      data: {
      	startDate: state_start,
      	endDate: state_end,
      	portfolioContributors: state_portfolioContributors_asAString,
      	externalStocks: state_externalStocks_asAString
      },

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
  
  					//IMPLEMENTATION NOTES: these functions have to use the state_ variables
  					//calling refreshGraph() will send the state as a request to GraphServlet
  					//and magically get exactly what you want to show up on the graph.
  					//refreshGraph() might not work currently. Right now, the GraphServlet only
  					//expects the request to have start/end date. It doesn't even check
  					//for portfolio contributors and external stocks. We can add that later.
  
  //#1: Add to portfolio contributors
  function addPortfolioContributor(stock) {
  	state_portfolioContributors.push(stock);
  	refreshGraph();
  }
  
  //#2: Remove from portfolio contributors, TODO
 
  //#3: Add an external stock
  function addExternalStock(stock) {
  	state_externalStocks.push(stock);
  	refreshGraph();
  }
  
  //#4: Remove an external stock, TODO
  
  //#5: Change the start date, TODO
  
  //#6: Change the end date, TODO
  
  
  
  
  
  
  
  
  
  

  function drawMainChart() {
    var data = google.visualization.arrayToDataTable(jsonArray);

    var options = {
      title: "Company Performance",
      curveType: "function",
      legend: { position: "bottom" },
      interpolateNulls: true,
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
    
    //Read the form input for the calendar: from date and to date
    var startDate = document.getElementById("fromDate").value;
    var endDate = document.getElementById("toDate").value;
    
    console.log("Yoooo");
    console.log("Start date requested: " + startDate);
    console.log("End date requested: " + endDate);
    
    //Eventually this part of the code will be replaced by state update and refreshGraph()
    $.ajax({
      url: "GraphServlet",
      type: "GET",
      data: {
      	startDate: startDate,
      	endDate: endDate 
      },

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