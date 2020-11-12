package csci310.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import csci310.Api;
import csci310.Portfolio;

@WebServlet("GraphServlet")
public class GraphServlet extends HttpServlet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String jsonArray;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {		
		//Read just the startDate and endDate parameters, eventually it will be more
		String startDateState = request.getParameter("startDate");
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
				}
			}
		}
		
//		System.out.println("start date: " + startDateState);
		String earliestDate = "";
		try {
			earliestDate = Portfolio.getEarliestTransactionDate(user_id);
//			System.out.println("earliest date: " + earliestDate);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		String startDate = startDateState;
		if(startDateState != null && startDateState.equals("-1")) { // if first time draw graph (ie user didn't specify dates)
			if(earliestDate == null) { // portfolio is empty. default to 3 months before end date
				String[] temp = endDate.split("-");
				int year = Integer.parseInt(temp[0]);
				int month = Integer.parseInt(temp[1]);
				int day = Integer.parseInt(temp[2]);
				month -= 3;
				if(month <= 0) {
					month += 12;
					year--;
				}
				String opt = "";
				if (month < 10) {
			      opt = "0";
			    }
				startDate = year + "-" + opt + month + "-" + day; // eg. format: 2020-01-01
			} else { // portfolio is NOT empty. default to earliest transaction date
				try {
					Date earliestD = new SimpleDateFormat("MM-dd-yyyy").parse(earliestDate);
					
					Calendar cal = Calendar.getInstance();
					cal.add(Calendar.YEAR, -1);
					cal.add(Calendar.DATE, -1);
					Date lastYear = cal.getTime();
					
					if(earliestD.compareTo(lastYear) < 0) {  // if earliestD is before last year
						earliestD = lastYear;
					}
					startDate = new SimpleDateFormat("yyyy-MM-dd").format(earliestD);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}
		
		if(startDate == null) {
			startDate = "2020-01-01";
		}

		if(endDate == null) {
			endDate = "2020-10-01";
		}
		
		// error check if start or end dates were not set (ie user did not select date)
		if(startDate.isEmpty() || endDate.isEmpty()) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			PrintWriter out = response.getWriter();
			out.print("Please enter valid start and end dates!");
			out.flush();
			return;
		}
		
		//Convert the dates to MM-DD-YYYY
//		System.out.println("108 start: " + startDate + ", end: " + endDate);
		String startYr = startDate.substring(0, 4);
		startDate = startDate.substring(5) + "-" + startYr;
		String endYr = endDate.substring(0, 4);
		endDate = endDate.substring(5) + "-" + endYr;
		
		//Commenting out sales and expenses graph
		//CreateArray();
		
		// error check start/end dates
		Date start = null, end = null;
		try {
			start = new SimpleDateFormat("MM-dd-yyyy").parse(startDate);
			end = new SimpleDateFormat("MM-dd-yyyy").parse(endDate);
		} catch (ParseException e) {
			System.out.println("In GraphServlet, can't parse start/end dates.");
		}
		// if start/end are null, or start is after end, or start is more than a year ago
		if(start != null && end != null) {
			if(start.compareTo(end) > 0) { 
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				PrintWriter out = response.getWriter();
				out.print("Start date must occur before end date!");
				out.flush();
				return;
			}
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.YEAR, -1);
			cal.add(Calendar.DATE, -1);
			Date lastYear = cal.getTime();
			if(start.compareTo(lastYear) < 0) {  // if start is before last year
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				PrintWriter out = response.getWriter();
				out.print("Start date must be within the past year!");
				out.flush();
				return;
			}
		}
		
		
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
			externalStocks = externalStocks.substring(0, externalStocks.length()-1);
			String[] strings = externalStocks.split(",");
			stocks = new ArrayList<String>(Arrays.asList(strings));
		}
		
		// check portfolio contributors stocks next
		ArrayList<String> portfolioStocks = new ArrayList<String>();
		if(portfolioContributors != null && portfolioContributors != "" && portfolioContributors.length() >= 2) {
			portfolioContributors = portfolioContributors.substring(0, portfolioContributors.length()-1);
			String[] strings = portfolioContributors.split(",");
			portfolioStocks = new ArrayList<String>(Arrays.asList(strings));
		}
		
		// add user's current porfolio to stocks arraylist
		stocks.add("PORTFOLIO_" + user_id);
		
		ArrayList<ArrayList<String>> dataset = Api.getMultipleLinesWithDateRange(stocks, startDate, endDate, portfolioStocks);
		jsonArray = Api.datasetToJSON(dataset);
		
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
