package csci310.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.annotation.WebServlet;
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
	
	protected void CreateArray() {
		List<List<String>> list = new ArrayList<List<String>>();
		List<String> title = new ArrayList<String>();
		title.add("Year");
		title.add("Sales");
		title.add("Expenses");
		list.add(title);
		
		List<String> data = new ArrayList<String>();
		data.add("2004");
		data.add("1000");
		data.add("404");
		list.add(data);
		
		List<String> data1 = new ArrayList<String>();
		data1.add("2005");
		data1.add("2340");
		data1.add("700");
		list.add(data1);
		
		List<String> data2 = new ArrayList<String>();
		data2.add("2006");
		data2.add("6894");
		data2.add("942");
		list.add(data2);
		
		Gson g = new Gson();
		jsonArray = g.toJson(list);
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		System.out.println("GraphServlet doGet");
		
		//Read just the startDate and endDate parameters, eventually it will be more
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		
		System.out.println("GraphServlet, startDate passed was: " + startDate);
		System.out.println("GraphServlet, endDate passed was: " + endDate);
		
		//Convert the dates to MM-DD-YYYY
		String startYr = startDate.substring(0, 4);
		startDate = startDate.substring(5) + "-" + startYr;
		String endYr = endDate.substring(0, 4);
		endDate = endDate.substring(5) + "-" + endYr;
		
		//Commenting out sales and expenses graph
		//CreateArray();
		//System.out.println("jsonArray: " + jsonArray);
		
		
		
		ArrayList<String> stocks = new ArrayList<String>();
		stocks.add("NTNX"); // so far only one stock can be displayed at a time
		stocks.add("JNJ");
		stocks.add("FB");
		
		//Print out what we will request
		System.out.println("GraphServlet, request package");
		for(int i = 0; i < stocks.size(); i++) {
			System.out.print(stocks.get(i) + " ");
		}
		System.out.println("");
		System.out.println("Converted Start date: " + startDate);
		System.out.println("Converted End date: " + endDate);
		
		ArrayList<ArrayList<String>> dataset = Api.getMultipleLinesWithDateRange(stocks, startDate, endDate);
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
