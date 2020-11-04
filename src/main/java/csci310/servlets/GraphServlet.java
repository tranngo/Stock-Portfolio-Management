package csci310.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import csci310.Api;

@WebServlet("GraphServlet")
public class GraphServlet extends HttpServlet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String jsonArray;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		System.out.println("GraphServlet doGet");
		
		//Read just the startDate and endDate parameters, eventually it will be more
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String portfolioContributors = request.getParameter("portfolioContributors");
		String externalStocks = request.getParameter("externalStocks"); //NTNX,JNJ,SLFS,
		
		Cookie cookies[] = request.getCookies();
		int user_id = -99;
		if(cookies != null) {
			for(int i = 0; i < cookies.length; i++) {
				System.out.println("Reading cookie number " + i);
				if(cookies[i].getName().equals("user_id")) {
					user_id = Integer.parseInt(cookies[i].getValue());
					System.out.println("Found the user_id cookie! Value found is " + user_id);
				}
			}
		}

		System.out.println("User id stored in cookie was " + user_id);
		System.out.println("GraphServlet, startDate passed was: " + startDate);
		System.out.println("GraphServlet, endDate passed was: " + endDate);
		
		if(startDate == null) {
			// System.out.println("Since start date was null, setting start to default date of 2020-01-01");
			startDate = "2020-01-01";
		}
		if(endDate == null) {
			// System.out.println("Since start date was null, setting start to default end date of 2020-10-01");
			endDate = "2020-10-01";
		}
		
		//Convert the dates to MM-DD-YYYY
		String startYr = startDate.substring(0, 4);
		startDate = startDate.substring(5) + "-" + startYr;
		String endYr = endDate.substring(0, 4);
		endDate = endDate.substring(5) + "-" + endYr;
		
		//Commenting out sales and expenses graph
		//CreateArray();
		// System.out.println("jsonArray: " + jsonArray);
		
		
		ArrayList<String> stocks = new ArrayList<String>();
		stocks.add("NTNX"); // so far only one stock can be displayed at a time
		stocks.add("JNJ");
		stocks.add("FB");
		
		//Testing out the state machine code for externalStocks
		if(externalStocks == null || externalStocks == "" || externalStocks.length() < 2) {
			System.out.println("External stocks passed by request was null! Using default values of NTNX, JNJ, and FB");
		}
		else {
			System.out.println("Received some external stocks " + externalStocks);
			externalStocks.substring(0, externalStocks.length()-1);
			String[] strings = externalStocks.split(",");
			stocks = new ArrayList<String>(Arrays.asList(strings));
		}
		
		// add user's current porfolio to stocks arraylist
		stocks.add("PORTFOLIO_" + user_id);
		
		//Print out what we will request
		System.out.println("GraphServlet, request package");
		for(int i = 0; i < stocks.size(); i++) {
			System.out.print(stocks.get(i) + " ");
		}
		 System.out.println("");
		 System.out.println("Converted Start date: " + startDate);
		 System.out.println("Converted End date: " + endDate);
		
		ArrayList<ArrayList<String>> dataset = Api.getMultipleLinesWithDateRange(stocks, startDate, endDate);
		System.out.println("Past the dataset line");
		jsonArray = Api.datasetToJSON(dataset);
		System.out.println("Real stock data jsonArray: " + jsonArray);
		
		PrintWriter out;
		out = response.getWriter();
		response.setContentType("application/json");
		out.print(jsonArray);
		out.flush();
		
//		response.setContentType("application/json");
		response.setStatus(HttpServletResponse.SC_OK);
	}
	
	protected String GetArray() {
		return jsonArray;
	}

}
