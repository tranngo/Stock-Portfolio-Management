google.charts.load("current", { packages: ["corechart"] });
  google.charts.setOnLoadCallback(drawMainChart);
  google.charts.setOnLoadCallback(refreshGraph);

  var jsonArray = [
    ["Year", "Example Stock"],
    ["2004", 1000],
    ["2005", 1170],
    ["2006", 660],
    ["2007", 1030],
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
  state_portfolioContributors = ["NTNX", "JNJ", "FB"];
  state_externalStocks = ["NTNX"];
  state_start = "-1";
  state_end = "-1";
  state_portfolioValue = "$0";
  state_percentChange = "0.0%";
  
  //Calling this function will take the "state" and pass it to GraphServlet as your request
  function refreshGraph() {

    console.log("Graph refresh requested");
    if(state_start === "-1") {
    	console.log("We have to set default dates");
    	//Referenced from: https://stackoverflow.com/questions/12409299/how-to-get-current-formatted-date-dd-mm-yyyy-in-javascript-and-append-it-to-an-i
	  	const monthNames = ["January", "February", "March", "April", "May", "June",
	        "July", "August", "September", "October", "November", "December"];
	    let dateObj = new Date();
	    let month = dateObj.getMonth() + 1;
	    month = month - 3;
	    if(month <= 0) {
	    	month = month + 12;
	    }
	
	    let day = String(dateObj.getDate()).padStart(2, '0');
	    let year = dateObj.getFullYear();
	    let opt = '';
	    if(month < 10) {
	    	opt = '0';
	    }
	    let output = year + '-' + opt + month + '-' + day;
	    state_start = output;
	    console.log("Setting default start date to: " + state_start);
	    
	    month = dateObj.getMonth() + 1;
	    day = String(dateObj.getDate()).padStart(2, '0');
	    year = dateObj.getFullYear();
	    opt = '';
	    if(month < 10) {
	    	opt = '0';
	    }
	    output = year + '-' + opt + month + '-' + day;
	    state_end = output;
	    console.log("Setting default end date to: " + state_end);
    }
    
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
        getMyCurrentPortfolioValue();
      },
    });
    return false;
  }
  
  					//These 8 functions should handle most of the work
  
  //#1: Add to portfolio contributors
  function addPortfolioContributor(stock) {
  	state_portfolioContributors.push(stock);
  	refreshGraph();
  }
  
  //#2: Remove from portfolio contributors
  function removePortfolioContributor(stock) {
  
  	console.log("Remove portfolio contributor called on " + stock);
  	console.log("state_portfolioContributors was previously " + state_portfolioContributors);
  
  	//Find that portfolio contributor
  	var pos = 0;
  	var toRemoveIndex = -1;
  	for(pos = 0; pos < state_portfolioContributors.length; pos++) {
  		if(stock === state_portfolioContributors[pos]) {
  			toRemoveIndex = pos;
  		}
  	}
  	
  	console.log("toRemoveIndex was determined to be " + toRemoveIndex);
  	
  	//Remove that portfolio contributor
  	if(toRemoveIndex != -1) {
  		state_portfolioContributors.splice(toRemoveIndex, 1);
  	}
  	
  	console.log("After removing " + stock + " state_portfolioContributors is now " + state_portfolioContributors);
 	console.log("Refreshing graph now");
 	refreshGraph();
  }
 
  //#3: Add an external stock
  function addExternalStock(stock) {
  	state_externalStocks.push(stock);
  	refreshGraph();
  }
  
  //#4: Remove an external stock
  function removeExternalStock(stock) {
  
  	console.log("Remove external stock called on " + stock);
  	console.log("state_externalStocks was previously " + state_externalStocks);
  
  	//Find that external stock in our list
  	var pos = 0;
  	var toRemoveIndex = -1;
  	for(pos = 0; pos < state_externalStocks.length; pos++) {
  		if(stock === state_externalStocks[pos]) {
  			toRemoveIndex = pos;
  		}
  	}
  	
  	console.log("toRemoveIndex was determined to be " + toRemoveIndex);
  	
  	//Remove that external stock
  	if(toRemoveIndex != -1) {
  		state_externalStocks.splice(toRemoveIndex, 1);
  	}
  	
  	console.log("After removing " + stock + " state_externalStocks is now " + state_externalStocks);
 	console.log("Refreshing graph now");
 	refreshGraph();
  }
  
  //#5: Change the start date
  function changeStartDate(newDate) {
  	console.log("Called changeStartDate, previously it was: " + state_start);
  	state_start = newDate;
  	console.log("Now it is: " + state_start);
  	// console.log("Refreshing graph");       Taking out for now
  	// refreshGraph();
  }
  
  //#6: Change the end date
  function changeEndDate(newDate) {
  	console.log("Called changeEndDate, previously it was: " + state_end);
  	state_end = newDate;
  	console.log("Now it is: " + state_end);
  	// console.log("Refreshing graph");		Taking out for now
  	// refreshGraph();
  }
  
  //#7: Add S&P 500 to external stocks
  function turnSpOn() {
  	console.log("Turn SP on was called");
  	addExternalStock("^GSPC");
  }
  
  //#8: Remove S&P 500 from external stocks
  function turnSpOff() {
  	console.log("Turn SP off was called");
  	removeExternalStock("^GSPC");
  }
  
  
  
  //Left side panel (Portfolio related add/remove/upload)
  function addToPortfolio(stock, quantity, dateOfPurchase, dateOfSelling) {
  		
  		//First, call the PortfolioServlet
  		$.ajax({
		      url: "PortfolioServlet",
		      type: "POST",
		      data: {
		      	type: "add",
		      	stock: stock,
		      	quantity: quantity,
		      	purchaseDate: dateOfPurchase,
		      	sellDate: dateOfSelling
		      },
		
		      success: function (result) {
		        console.log("Yay! Stock transaction successfully added to portfolio");
		        
		        //Next, add to portfolio contributors
		 		addPortfolioContributor(stock);
		      },
		 });
		 
  } 
  
  //Call this with the red 'X'
  function removeFromPortfolio(stock) {
  		//First, call the PortfolioServlet
  		$.ajax({
		      url: "PortfolioServlet",
		      type: "POST",
		      data: {
		      	type: "remove",
		      	stock: stock
		      },
		
		      success: function (result) {
		        console.log("Yay! Stock successfully removed from portfolio");
		        
		        //Next, remove from portfolio contributors
		 		removePortfolioContributor(stock);
		      },
		 });
  }
  
  //Read the CSV file
  //Referenced from: https://stackoverflow.com/questions/7431268/how-to-read-data-from-csv-file-using-javascript
  function readFile(file) {
	    var fileData = file.split(/\r\n|\n/);
	    var lines = [];
	    var i = 1;

		//Skip the headers
	    for(i = 1; i < fileData.length; i++) 
	    {
	    	//Get one line of data
	        var data = fileData[i].split(',');
	        
	        //There should only be 4 entries
	        if (data.length == 4) {
	            var oneRow = [];
	            var j = 0;
	            
	            for (j = 0; j < 4; j++) {
	                oneRow.push(data[j]);
	            }
	            lines.push(oneRow);
	            console.log("Line " + i + " is " + oneRow);
	        }
	    }
	}
	
	//Get the portfolio value to display on the webpage
	function getMyCurrentPortfolioValue() {
		//First, call PortfolioServlet with type="getPortfolioValue"
		$.ajax({
		      url: "PortfolioServlet",
		      type: "POST",
		      data: {
		      	type: "getPortfolioValue"
		      },
		
		      success: function (result) {
		        console.log("Yay! My portfolio value is retrieved");
		        var value = result;
		        console.log("It is " + value);
		        state_portfolioValue = value;
		        
		        $.ajax({
				      url: "PortfolioServlet",
				      type: "POST",
				      data: {
				      	type: "getPercentChange"
				      },
				
				      success: function (result) {
				        console.log("Yay! My percent change is retrieved");
				        var per = result;
				        console.log("It is " + per);
				        state_percentChange = per;
				        
				      },
				 });
				        
		        
		      },
		 });
		
	}
	
	
  

  function drawMainChart() {
    var data = google.visualization.arrayToDataTable(jsonArray);

    var options = {
      title: "Stock Performance",
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
    
    //Read the form input for the calendar: from date and to date
    var startDate = document.getElementById("fromDate").value;
    var endDate = document.getElementById("toDate").value;
    
    console.log("Yoooo");
    console.log("Start date requested: " + startDate);
    console.log("End date requested: " + endDate);
    
    //Update state and refresh graph
    changeStartDate(startDate);
    changeEndDate(endDate);
    refreshGraph();
    return false;
  }
 

$("#add-stock-button").on("click", function() {
	$(".modal-title").html("Add Stock Transaction");
	$("#modal-confirm-button").html("Confirm");
	$("#add-modal-content").attr("class", "display-block");
	$("#upload-file-modal-content").attr("class", "display-none");
	$("#add-external-stock-modal-content").attr("class", "display-none");
	$("#remove-external-stock-modal-content").attr("class", "display-none");
	
	$("#modal-confirm-button").data("type", "addStock");
	
	$("#mainModal").modal({
		backdrop: true,
		keyboard: true,
		focus: true,
		show: true
	});
});

$("#upload-file-button").on("click", function() {
	$(".modal-title").html("Upload File");
	$("#modal-confirm-button").html("Upload");
	$("#add-modal-content").attr("class", "display-none");
	$("#upload-file-modal-content").attr("class", "display-block");
	$("#add-external-stock-modal-content").attr("class", "display-none");
	$("#remove-external-stock-modal-content").attr("class", "display-none");
	
	$("#modal-confirm-button").data("type", "uploadFile");
	
	$("#mainModal").modal({
		backdrop: true,
		keyboard: true,
		focus: true,
		show: true
	});
});

$("#add-external-stock-button").on("click", function() {
	$(".modal-title").html("Add External Stock");
	$("#modal-confirm-button").html("View Stock");
	$("#add-modal-content").attr("class", "display-none");
	$("#upload-file-modal-content").attr("class", "display-none");
	$("#add-external-stock-modal-content").attr("class", "display-block");
	$("#remove-external-stock-modal-content").attr("class", "display-none");
	
	$("#modal-confirm-button").data("type", "addExternal");
	
	$("#mainModal").modal({
		backdrop: true,
		keyboard: true,
		focus: true,
		show: true
	});
});

$("#remove-external-stock-button").on("click", function() {
	$(".modal-title").html("Remove External Stock");
	$("#modal-confirm-button").html("Remove Stock");
	$("#add-modal-content").attr("class", "display-none");
	$("#upload-file-modal-content").attr("class", "display-none");
	$("#add-external-stock-modal-content").attr("class", "display-none");
	$("#remove-external-stock-modal-content").attr("class", "display-block");
	
	$("#modal-confirm-button").data("type", "removeExternal");
	
	$("#mainModal").modal({
		backdrop: true,
		keyboard: true,
		focus: true,
		show: true
	});
});

$("#modal-confirm-button").on("click", function() {

	if ($(this).data("type") === "addStock") {
		//Change
		var stock = $("#stock-name-input").val();
		var quantity = $("#stock-quantity-input").val();
		var dateOfPurchase = $("#stock-purchase-date-input").val();
		var dateOfSelling = $("#stock-sell-date-input").val();
		addToPortfolio(stock, quantity, dateOfPurchase, dateOfSelling);
	} else if ($(this).data("type") === "uploadFile") {
		//Change
		console.log("Upload file was hit");
		var file = $("#fileUpload").val();
		readFile(file);
	} else if ($(this).data("type") === "addExternal") {
		addExternalStock($("#add-external-stock-name-input").val());
	} else if ($(this).data("type") === "removeExternal") {
		removeExternalStock($("#remove-external-stock-name-input").val());
	}
	
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
		turnSpOn();
	} else {
		$(this).attr("class", $(this).attr("class").replace("btn-secondary", "btn-primary"));
		turnSpOff();
	}
})
