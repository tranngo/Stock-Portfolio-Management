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
		System.out.println("PortfolioServlet doPost");
		
		//Read just the startDate and endDate parameters, eventually it will be more
		String type = request.getParameter("type");
		
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
		System.out.println("PortfolioServlet, type of request is: " + type);
		
		if(type.equals("add")) {
			String stock = request.getParameter("stock");
			String quantityStr = request.getParameter("quantity");
			String purchaseDate = request.getParameter("purchaseDate");
			String sellDate = request.getParameter("sellDate");
			
			System.out.println("Stock is: " + stock);
			System.out.println("Quantity is: " + quantityStr);
			System.out.println("Purchase date is: " + purchaseDate);
			System.out.println("Sell date is: " + sellDate);
			
			if(!Api.isNumeric(quantityStr)) {
				System.out.println("Quantity is not a number");
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				return;
			}
			else {
				int quantity = Integer.parseInt(quantityStr);
				int result = Portfolio.addStock(user_id, stock, quantity, purchaseDate, sellDate);
				System.out.println("Called Portfolio.addStock, result was: " + result);
				
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
				System.out.println("Yup");
				return;
			}
		}
		else if(type.equals("remove")) {
			String stock = request.getParameter("stock");
			
			System.out.println("Stock is: " + stock);
			
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
			System.out.println("Upload not yet implemented");
		}
		else {
			System.out.println("Type of request is not recognized, it should have been add/remove/upload");
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		
	}

}
