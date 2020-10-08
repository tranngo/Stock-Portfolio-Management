package csci310;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Portfolio {
	/*
	 * Function #1: add a stock to the user's portfolio. First make sure that the stock
	 * name is valid by using isValidStock() in Api.java. Next, make sure the quantity is
	 * a valid number. 
	 * Important: If the user already has this stock, we don't want to update that.
	 * We want to make a redundant entry into the database.
	 * Also Important: When you add into the database, prefix the stock name with
	 * "BOUGHT_" this will be important later on for calculating historical prices.
	 * 
	 * Valid DB:
	 * [user_id, "BOUGHT_NTNX", 7, "02-01-2020"]
	 * [user_id, "BOUGHT_NTNX", 100, "03-03-2020"]
	 * 
	 * parameters: user_id, stock, quantity, and date of purchase
	 * returns: 1 if successfully inserted, 0 if not, later we will add error codes
	 */
	public static int addStock(int user_id, String stock, int quantity, String dateOfPurchase)
	{
		return 0;
	}
	
	/*
	 * Function #2: sell a stock from the user's portfolio. First make sure that the stock
	 * name is valid by using isValidStock() in Api.java. Next, make sure the quantity is
	 * a valid number (not negative). You also want to call the database with user_id and 
	 * see if the user has enough of these stocks in the first place to sell them.
	 * Important: We don't actually remove anything from the database when selling. We just
	 * make an entry that says "SOLD_NTNX" (see below example). This will be important later.
	 * Also Important: When you add an entry into the database, prefix the stock name with
	 * "SOLD_" this will be important later on for calculating historical prices.
	 * 
	 * Valid DB:
	 * [user_id, "BOUGHT_NTNX", 7, "02-28-2020"]
	 * [user_id, "BOUGHT_NTNX", 100, "03-03-2020"]
	 * [user_id, "SOLD_NTNX", 100, "03-04-2020"]
	 * 
	 * parameters: user_id, stock, quantity, and date of selling
	 * returns: 1 if successfully sold, 0 if not, later we will add error codes
	 */
	public static int sellStock(int user_id, String stock, int quantity, String dateOfSelling)
	{
		return 0;
	}
	
	/*
	 * Function #3: retrieves the stocks that the user currently holds. No "BOUGHT_" and
	 * "SOLD_" tags, just the stock name. The key is to scan the database for all stocks
	 * with a matching user_id. This will return a bunch of "boughts" and "solds", then
	 * you have to figure out after all the buy/sell transactions, what the user
	 * actually has left. This function might be a little tricky.
	 * 
	 * Example Output:
	 * ["Stock", "Quantity"]
	 * ["NTNX", "3"]
	 * ["SLSF", "4"]
	 * 
	 * parameters: user_id
	 * returns: a ArrayList<ArrayList<String>> but basically a nx2 array
	 */
	public static ArrayList<ArrayList<String>> retrieveMyCurrentPortfolio(int user_id)
	{	
		// init portfolio
		ArrayList<ArrayList<String>> portfolio = new ArrayList<ArrayList<String>>();
		ArrayList<String> header = new ArrayList<String>(
				Arrays.asList("Stock", "Quantity"));
		portfolio.add(header);
		
		// hashmap: stockname : stockquantity
		HashMap<String, Integer> hmap = new HashMap<>(); 
					 
		// connect to mysql
		Connection con = JDBC.connectDB();
//		if(con != null) {
//			try {
//				// query stocks table for user id
//				PreparedStatement ps = con.prepareStatement("SELECT * FROM stocks WHERE user_id = ?");
//				ps.setInt(1, user_id);
//				ResultSet rs = ps.executeQuery();
//	
//				// while there are stocks in the portfolio
//				while(rs.next()) {
//					// example rs returned: [user_id, "BOUGHT_NTNX", 7, "02-01-2020"]
//					// parse string for stock name and quantity
//					String stockName = "";
//					String stockStr = rs.getString(2); // either BOUGHT_STOCK or SOLD_STOCK
//					int stockQuantInt = rs.getInt(3); // stock quantity
//					
//					String stocks[] = stockStr.split("_");
//					if(stocks.length == 2) { // if successful splitting
//						// example stocks[]: ["BOUGHT", "NTNX"]
//						// bought/sold is at index 0, stock name is at index 1
//						String stockStatus = stocks[0];
//						stockName = stocks[1];
//						
//						// if stock doesn't exist in hashmap yet
//						if(!hmap.containsKey(stockName)) {
//							// if sold stock, decrease quant ('bought' should be queried first, but just in case)
//							if(stockStatus.toLowerCase().equals("sold")) {
//								stockQuantInt *= -1; // make quantity neg (aka subtract)
//							} // else, user bought stock. no calculation needed here
//							
//							hmap.put(stockName, stockQuantInt);
//						// else, already exists. need to calculate quantity
//						} else {
//							int curQuant = hmap.get(stockName);
//							
//							// if sold stock, decrease quant
//							if(stockStatus.toLowerCase().equals("sold")) {
//								stockQuantInt *= -1; // make quantity neg (aka subtract)
//							} // else, user bought stock. no calculation needed here
//							
//							// replace quantity for stockName in hmap
//							hmap.put(stockName, curQuant + stockQuantInt);
//						}
//					} else {
//						System.out.println("Failure retrieving current stock in portfolio.");
//					}
//				} // end while
//	        } catch (SQLException e) {
//	        	System.out.println("Error querying stock data from DB when retrieving current portfolio.");
//	        	e.printStackTrace();
//	        }
//		} // end if con != null
//		
//		// traverse hashmap
//		for(Map.Entry<String, Integer> mapElement : hmap.entrySet()) { 
//			// get stock info from map
//            String stockName = (String)mapElement.getKey(); 
//            int stockQuant = (int)mapElement.getValue(); 
//  
//            // add current stock to portfolio
//            ArrayList<String> curStock = new ArrayList<String>(
//    				Arrays.asList(stockName, String.valueOf(stockQuant)));
//            portfolio.add(curStock);
//        } 
		
		return portfolio;
	}
	
	/*
	 * Function #4: gets what your portfolio is worth right now. We basically call
	 * the above function which is Function 3.
	 * Then you call the Api.java function getPriceOf() to determine what each stock 
	 * you currently hold is worth. This function might be a little tricky.
	 * 
	 * Output: "$1,077.45"
	 * 
	 * parameters: user_id
	 * returns: current value of portfolio
	 */
	public static String getCurrentPortfolioValue(int user_id)
	{
		ArrayList<ArrayList<String>> portfolio = retrieveMyCurrentPortfolio(user_id);
		double portfolioVal = 0;
		
		// example portfolio value: { ["Stock", "Quantity"], ["NTNX", "3"], ["SLSF", "4"] }
		
		// starting at index 1, ignoring header strings
//		for(int i = 1; i < portfolio.size(); i++) {
//			// get current stock name and quantity
//			String stockName = portfolio.get(i).get(0);
//			int stockQuant = Integer.parseInt(portfolio.get(i).get(1));
//			
//			// api call to get price of stock
//			double stockVal = 0;
//			try {
//				stockVal = Double.parseDouble(Api.getCurrentPriceOf(stockName));
//			} catch (NumberFormatException e) {
//				e.printStackTrace();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//			
//			// update running total of portfolio value
//			portfolioVal += stockVal * stockQuant;
//		}
		return String.valueOf(portfolioVal);
	}
	
	/*
	 * Function #5: gets the historical value of the portfolio which will be shown on
	 * one of our graphs. You first find the earliest "BOUGHT_" date and then you find
	 * the latest (most recent) overall transaction (can be either bought or sold). This
	 * allows to have a "start" date and an "end" date for our portfolio.
	 * Then we iterate between start date and end date for the portfolio,
	 * incrementing by a day each time (which is tricky to do with MM-DD-YYYY).
	 * Then you call Function 7 which is below for each date and add that to the 
	 * 2xn table that you are going to return.
	 * 
	 * Output:
	 * ["Date", "Value"]
	 * ["01-30-2020", "2130.11"]
	 * ["01-31-2020", "3233.59"]
	 * ["02-01-2020", "3539.59"]
	 * ["02-02-2020", "4133.59"]
	 * 
	 * 
	 * parameters: user_id
	 * returns: ArrayList<ArrayList<String> > basically a n x 2 array
	 */
	public static ArrayList<ArrayList<String>> getFullLineForPortfolio(int user_id)
	{
//		Date start = null, end = null;
//		
//		// init portfolio
//		ArrayList<ArrayList<String>> portfolio = new ArrayList<ArrayList<String>>();
//		ArrayList<String> header = new ArrayList<String>(
//				Arrays.asList("Date", "Value"));
//		portfolio.add(header);
//		
//		// connect to mysql
//		Connection con = JDBC.connectDB();
//		if(con != null) {
//			try {
//				// query stocks table for user id
//				PreparedStatement ps = con.prepareStatement("SELECT * FROM stocks WHERE user_id = ?");
//				ps.setInt(1, user_id);
//				ResultSet rs = ps.executeQuery();
//				
//				String transDateStr = "";
//				String stockStr = "";
//	
//				// while there are stocks in the portfolio, find start and end dates
//				while(rs.next()) {
//					// example rs returned: [user_id, "BOUGHT_NTNX", 7, "02-01-2020"]
//					transDateStr = rs.getString(4); // date
//					stockStr = rs.getString(2); // bought/sold_stock
//					if(start == null && end == null) { // need to init start and end dates
//						String stocks[] = stockStr.split("_");
//						if(stocks.length == 2) { // if successful splitting
//							// example stocks[]: ["BOUGHT", "NTNX"]
//							// bought/sold is at index 0, stock name is at index 1
//							String stockStatus = stocks[0];
//							if(stockStatus.toLowerCase().equals("bought")) {
//								start = new SimpleDateFormat("MM-dd-yyyy").parse(transDateStr);
//								end = new SimpleDateFormat("MM-dd-yyyy").parse(transDateStr);
//							}
//						} else {
//							System.out.println("Failure retrieving current stock in portfolio.");
//						}
//					} else {
//						Date transDate = new SimpleDateFormat("MM-dd-yyyy").parse(transDateStr);
//						if(transDate.before(start)) {
//							start = transDate;
//						}
//						if(transDate.after(end)) {
//							end = transDate;
//						}
//					}
//				}
//	        } catch (SQLException e) {
//	        	System.out.println("Error querying stock data from DB when retrieving current portfolio.");
//	        	e.printStackTrace();
//	        } catch (ParseException e) {
//	        	System.out.println("Error parsing date.");
//				e.printStackTrace();
//			}
//		} // end if con != null
//		
//		// loop through dates to calculate portfolio values
//		LocalDate startDate = start.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//		LocalDate endDate = end.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//		for(LocalDate date = startDate; date.isBefore(endDate); date = date.plusDays(1)) {
//			SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");  
//		    String dateStr = formatter.format(date);  
//		    String portfolioVal = getPortfolioValueOnADate(user_id, dateStr);
//		    
//		    // add current date/value to portfolio
//            ArrayList<String> curVal = new ArrayList<String>(
//    				Arrays.asList(dateStr, portfolioVal));
//            portfolio.add(curVal);
//		}
//		
//		return portfolio;
		return null;
	}
	
	/*
	 * Function #6: gets the historical value of a portfolio but you can specify
	 * a start and end date. Pretty much you just call Function 5 and then
	 * remove all the entries where the date falls out of the range.
	 * 
	 * parameters: user_id, start, end
	 * returns: ArrayArrayList<ArrayArrayList<String> > basically a n x 2 array
	 */
	public static ArrayList<ArrayList<String>> getLineForPortfolioWithDateRange(int user_id, String start, String end)
	{
//		ArrayList<ArrayList<String>> portfolioFull = getFullLineForPortfolio(user_id);
//		ArrayList<ArrayList<String>> portfolioRanged = new ArrayList<ArrayList<String>>();
//		portfolioRanged.add(portfolioFull.get(0)); // add header: ["Date", "Value"]
//		
//		try {
//			// parse date strings as Dates
//			Date startDate = new SimpleDateFormat("MM-dd-yyyy").parse(start);
//			Date endDate = new SimpleDateFormat("MM-dd-yyyy").parse(end); 
//			
//			// starting at index 1, ignoring header strings, iterate through whole portfolio
//			for(int i = 1; i < portfolioFull.size(); i++) {
//				String date = portfolioFull.get(i).get(0);
//				Date curDate = new SimpleDateFormat("MM-dd-yyyy").parse(date);
//				
//				// if date is within range: if start <= date <= end
//				if(!startDate.after(curDate) && !endDate.before(curDate)) {
//					// add to portfolio for ranged dates
//					portfolioRanged.add(portfolioFull.get(i));
//				}
//			}
//		} catch (ParseException e) {
//			System.out.println("Error parsing dates");
//			e.printStackTrace();
//		} 
//		
//		return portfolioRanged;
		return null;
	}
	
	
	/*
	 * Function #7: get the portfolio value on a single date. Let's say today was "date",
	 * based on the bought/sold transactions that have happened until now, what is my
	 * portfolio worth today. Go through the database, look for any transactions that
	 * may have happened before this date and just collect them for now. Then go through
	 * that ArrayList and do some calculations to see what stocks you have now. Then call
	 * the Api.java getPriceOf() to determine what each stock you hold is worth.
	 * You might want to use Function 4 as a guide since it is similar.
	 * Important, but tricky function.
	 * 
	 * Output: "$1,077.45"
	 * 
	 * parameters: user_id, date (there might not be any transactions that happened on this date)
	 * returns: value of portfolio on that date
	 */
	public static String getPortfolioValueOnADate(int user_id, String date)
	{
		return "";
	}
	
	/*
	 * Function #:
	 * 
	 * parameters:
	 * returns:
	 */
}
