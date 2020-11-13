package csci310.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import csci310.Api;
import csci310.Portfolio;

@WebServlet("PortfolioServlet")
public class PortfolioServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String jsonArray;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		//Read just the startDate and endDate parameters, eventually it will be more
		String type = request.getParameter("type");
		
		Cookie cookies[] = request.getCookies();
		int user_id = -99;
		if(cookies != null) {
			for(int i = 0; i < cookies.length; i++) {
				if(cookies[i].getName().equals("user_id")) {
					user_id = Integer.parseInt(cookies[i].getValue());
				}
			}
		}
		
		if(type.equals("add")) {
			String stock = request.getParameter("stock");
			String quantityStr = request.getParameter("quantity");
			String purchaseDate = request.getParameter("purchaseDate");
			String sellDate = request.getParameter("sellDate");
			
			String startYr = purchaseDate.substring(0, 4);
			purchaseDate = purchaseDate.substring(5) + "-" + startYr;
			String endYr = sellDate.substring(0, 4);
			sellDate = sellDate.substring(5) + "-" + endYr;
			
			if(!Api.isNumeric(quantityStr)) {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				return;
			}
			else {
				int quantity = Integer.parseInt(quantityStr);
				int result = Portfolio.addStock(user_id, stock, quantity, purchaseDate, sellDate);
				
				PrintWriter out = response.getWriter();
				if(result == 1) {
					out.print("Successfully added stock transaction");
					out.flush();
				}
				else {
					out.print("Problem adding stock transaction");
					out.flush();
				}
				response.setStatus(HttpServletResponse.SC_OK);
				return;
			}
		}
		else if(type.equals("remove")) {
			String stock = request.getParameter("stock");
			
			int result = Portfolio.sellStock(user_id, stock);
			PrintWriter out = response.getWriter();
			if(result == 1) {
				out.print("Successfully removed stock");
				out.flush();
			}
			else {
				out.print("Problem removing stock");
				out.flush();
			}
			response.setStatus(HttpServletResponse.SC_OK);
			return;
		}
		else if(type.equals("upload")) {

		}
		else if(type.equals("getPortfolioValue")) {
			//All we need is the user_id
			String currentPortfolioValue = "$" + Portfolio.getCurrentPortfolioValue(user_id);
			
			System.out.println("getPortfolioValue: " + currentPortfolioValue);
			
			PrintWriter out = response.getWriter();
			out.print(currentPortfolioValue);
			out.flush();
			response.setStatus(HttpServletResponse.SC_OK);
			return;
		}
		else if(type.equals("getPercentChange")) {
			//Referenced from: https://stackoverflow.com/questions/38955993/how-to-get-yesterday-date
			//All we need is the user_id
			//MISSING ERROR CHECKING
			
			String currentPortfolioValue = Portfolio.getCurrentPortfolioValue(user_id);
			
			//An additional safety check
			if(currentPortfolioValue.equals("0.0") || currentPortfolioValue.equals("0.00"))
			{
				String percentChangeAsStr = "0.0%";
				
				PrintWriter out = response.getWriter();
				out.print(percentChangeAsStr);
				out.flush();
				response.setStatus(HttpServletResponse.SC_OK);
				return;
			}
			
			SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy");
			Calendar calendar = Calendar.getInstance();
			
			calendar.add(Calendar.DATE, -1);
			
			String yesterdayDate = format.format(calendar.getTime());
			String yesterdayPortfolioValue = Portfolio.getPortfolioValueOnADate(user_id, yesterdayDate);
			
			//An additional safety check
			if(yesterdayPortfolioValue.equals("NULL"))
			{
				String percentChangeAsStr = "0.0%";
				
				PrintWriter out = response.getWriter();
				out.print(percentChangeAsStr);
				out.flush();
				response.setStatus(HttpServletResponse.SC_OK);
				return;
			}
			
			double todayValue = Double.parseDouble(currentPortfolioValue);
			double yesterdayValue = Double.parseDouble(yesterdayPortfolioValue);
			
			double percentChange = 999.99;
			if(yesterdayValue != 0.0) {
				percentChange = (todayValue - yesterdayValue) / (yesterdayValue);
			}
			//initially 0.47734
			//we want 0.47
			//0.47734*100 = 47.734 cast to int to get 47. Then divided by 100
			percentChange = ((int)(percentChange*100))/(100.0);
			
			String percentChangeAsStr = percentChange + "%";
			
			System.out.println("getPercentChange: " + percentChangeAsStr);
			
			PrintWriter out = response.getWriter();
			out.print(percentChangeAsStr);
			out.flush();
			response.setStatus(HttpServletResponse.SC_OK);
			return;
		}
		else if(type.equals("getPortfolioList")) {
			//All we need is the user_id
			ArrayList<ArrayList<String>> myPortfolio = Portfolio.retrieveCurrentPortfolio(user_id);
			
			//Collect just the stocks
			String portfolioList = "";
			for(int i = 1; i < myPortfolio.size(); i++)
			{
				portfolioList += myPortfolio.get(i).get(0);
				if(i != myPortfolio.size() - 1)
				{
					portfolioList += ",";
				}
			}
			
			
			PrintWriter out = response.getWriter();
			out.print(portfolioList);
			out.flush();
			response.setStatus(HttpServletResponse.SC_OK);
			return;
		}
		else if(type.equals("isValidStock")) {
			//All we need is the stock name
			String stock = request.getParameter("stock");
			
			boolean isValid = true;
			try {
				isValid = Api.isValidStock(stock);
			}
			catch(IOException ie) {
				System.out.println("PortfolioServlet isValidStock branch problem");
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				return;
			}
			
			String answer = "";
			if(isValid == true) {
				answer = "GOOD";
			}
			else {
				answer = "BAD";
			}
			
			PrintWriter out = response.getWriter();
			out.print(answer);
			out.flush();
			response.setStatus(HttpServletResponse.SC_OK);
			return;
			
		}
		else {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		
	}

}
