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
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
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
	 * 
	 * Valid DB:
	 * [id, user_id, "bought", "NTNX", 7, "02-01-2020"]
	 * [id, user_id, "bought", "NTNX", 100, "03-03-2020"]
	 * 
	 * parameters: user_id, stock, quantity, and date of purchase
	 * returns: 1 if successfully inserted, 0 if not, later we will add error codes
	 */
	//Edit: params increased
	public static int addStock(int userId, String stock, int quantity, String dateOfPurchase, String dateOfSelling)
	{
		// check stock name & quantity valid
		Boolean validStock = false;
		try {
			 validStock= Api.isValidStock(stock);
			 if(!validStock || quantity <= 0) {
				return 0;
			}
		} catch (IOException e1) {
			e1.printStackTrace();
			return 0;
		}
		
		// connect to mysql
		JDBC db = new JDBC();
		Connection con = db.connectDB("com.mysql.cj.jdbc.Driver", "jdbc:mysql://remotemysql.com:3306/DT6BLiMGub","DT6BLiMGub","W1B4BiSiHP");
		
		if(con != null) {
			try {
				//edit: use stocks_new instead, and the whole code block is changed
			    // insert entry into users table
			    PreparedStatement ps = con.prepareStatement("INSERT into stocks_new (user_id, name, quantity, dateOfPurchase, dateOfSelling) VALUES (?, ?, ?, ?, ?)");
			    ps.setInt(1, userId);
			    ps.setString(2, stock);
			    ps.setInt(3, quantity);
			    ps.setString(4, dateOfPurchase);
			    ps.setString(5, dateOfSelling);
			    ps.execute();
			    con.close();
			    return 1;
			} catch (SQLException e) {
				// System.out.println("Error inserting user data to DB when adding to stocks_new.");
				e.printStackTrace();
			} finally {
	            try {
	                if(con != null) {
	                    con.close();
	                }
	            } catch (SQLException ex) {
	                // System.out.println(ex.getMessage());
	            }
	        }
		}
		
		return 0;
	}
	
	/*
	 * Function #2: sell a stock from the user's portfolio. First make sure that the stock
	 * name is valid by using isValidStock() in Api.java. Next, make sure the quantity is
	 * a valid number (not negative). You also want to call the database with user_id and 
	 * see if the user has enough of these stocks in the first place to sell them.
	 * Important: We don't actually remove anything from the database when selling.
	 * 
	 * Valid DB:
	 * [id, user_id, "bought", "NTNX", 7, "02-01-2020"]
	 * [id, user_id, "bought", "NTNX", 100, "03-03-2020"]
	 * [id, user_id, "sold", "NTNX", 7, "03-04-2020"]
	 * 
	 * parameters: user_id, stock, quantity, and date of selling
	 * returns: 1 if successfully sold, 0 if not, later we will add error codes
	 */
	//Edit: params reduced
	public static int sellStock(int userId, String stock)
	{
		// check stock name valid
		try {
			 if(!Api.isValidStock(stock)) {
				return 0;
			}
		} catch (IOException e1) {
			e1.printStackTrace();
			return 0;
		}
		
		//Edit: remove checking for a valid quantity, since that is no longer a param
		
		// connect to mysql
		JDBC db = new JDBC();
		Connection con = db.connectDB("com.mysql.cj.jdbc.Driver", "jdbc:mysql://remotemysql.com:3306/DT6BLiMGub","DT6BLiMGub","W1B4BiSiHP");
		
		if(con != null) {
			try {
				//edit: actually call "delete from" instead
			    // insert entry into users table
			    PreparedStatement ps = con.prepareStatement("DELETE FROM stocks_new WHERE user_id=? AND name=?");
			    ps.setInt(1, userId);
			    ps.setString(2, stock);
			    ps.execute();
			    con.close();
			    return 1;
			} catch (SQLException e) {
				// System.out.println("Error inserting user data to DB when adding to stocks.");
				e.printStackTrace();
			} finally {
	            try {
	                if(con != null) {
	                    con.close();
	                }
	            } catch (SQLException ex) {
	                // System.out.println(ex.getMessage());
	            }
	        }
		}
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
	public static ArrayList<ArrayList<String>> retrieveCurrentPortfolio(int userId)
	{	
		// init portfolio
		ArrayList<ArrayList<String>> portfolio = new ArrayList<ArrayList<String>>();
		ArrayList<String> header = new ArrayList<String>(
				Arrays.asList("Stock", "Quantity"));
		portfolio.add(header);
		
		// hashmap: stockname : stockquantity
		HashMap<String, Integer> hmap = new HashMap<>(); 
					 
		// connect to mysql
		JDBC db = new JDBC();
		Connection con = db.connectDB("com.mysql.cj.jdbc.Driver", "jdbc:mysql://remotemysql.com:3306/DT6BLiMGub","DT6BLiMGub","W1B4BiSiHP");
		if(con != null) {
			try {
				// query stocks table for user id
				PreparedStatement ps = con.prepareStatement("SELECT * FROM stocks_new WHERE user_id = ?");
				ps.setInt(1, userId);
				ResultSet rs = ps.executeQuery();
	
				// while there are stocks in the portfolio
				while(rs.next()) {
					
					//Edit: removed the previous code checking for "bought" and "sold"
					
					
					// example rs returned: [id, user_id, "NTNX", 7, "02-01-2020", "03-05-2020"]
					// parse string for stock name and quantity
					String stockName = rs.getString(3);
					int stockQuantInt = rs.getInt(4);
					hmap.put(stockName, stockQuantInt);
					
				} // end while
	        } catch (SQLException e) {
	        	// System.out.println("Error querying stock data from DB when retrieving current portfolio.");
	        	e.printStackTrace();
	        } finally {
	            try {
	                if(con != null) {
	                    con.close();
	                }
	            } catch (SQLException ex) {
	                // System.out.println(ex.getMessage());
	            }
	        }
		} // end if con != null
		
		// traverse hashmap
		for(Map.Entry<String, Integer> mapElement : hmap.entrySet()) { 
			// get stock info from map
            String stockName = (String)mapElement.getKey(); 
            int stockQuant = (int)mapElement.getValue(); 
  
            if(stockQuant != 0) {
	            // add current stock to portfolio
	            ArrayList<String> curStock = new ArrayList<String>(
	    				Arrays.asList(stockName, String.valueOf(stockQuant)));
	            portfolio.add(curStock);
            }
        } 
		
		return portfolio;
	}
	
	/*
	 * Function #4: retrieves the stocks that the user holds on a given date. No "BOUGHT_" and
	 * "SOLD_" tags, just the stock name. The key is to scan the database for all stocks
	 * with a matching user_id before or on the given date. You have to figure out after all the
	 * buy/sell transactions, what the user actually has left. Exactly like Function #3, but
	 * with a date restriction.
	 * 
	 * Example Output:
	 * ["Stock", "Quantity"]
	 * ["NTNX", "3"]
	 * ["SLSF", "4"]
	 * 
	 * parameters: user_id
	 * returns: a ArrayList<ArrayList<String>> but basically a nx2 array
	 */
	public static ArrayList<ArrayList<String>> retrievePortfolioOnADate(int user_id, String date)
	{	
		// init portfolio
		ArrayList<ArrayList<String>> portfolio = new ArrayList<ArrayList<String>>();
		ArrayList<String> header = new ArrayList<String>(
				Arrays.asList("Stock", "Quantity"));
		portfolio.add(header);
		
		// hashmap: stockname : stockquantity
		HashMap<String, Integer> hmap = new HashMap<>(); 
					 
		// connect to mysql
		JDBC db = new JDBC();
		Connection con = db.connectDB("com.mysql.cj.jdbc.Driver", "jdbc:mysql://remotemysql.com:3306/DT6BLiMGub","DT6BLiMGub","W1B4BiSiHP");
		if(con != null) {
			try {
				// query stocks table for user id
				PreparedStatement ps = con.prepareStatement("SELECT * FROM stocks_new WHERE user_id = ?");
				ps.setInt(1, user_id);
				ResultSet rs = ps.executeQuery();
	
				// while there are stocks in the portfolio
				while(rs.next()) {
					// date should be in between transBuyDate and transSellDate
					String transBuyDateStr = rs.getString(5); // purchase date
					String transSellDateStr = rs.getString(6); // sell date
					
					SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy");
					Calendar transBuyDate = Calendar.getInstance();
					Calendar transSellDate = Calendar.getInstance();
					Calendar selectedDate = Calendar.getInstance();
					try {
						transBuyDate.setTime(format.parse(transBuyDateStr));
						transSellDate.setTime(format.parse(transSellDateStr));
						selectedDate.setTime(format.parse(date));
					} catch (ParseException e1) {
						e1.printStackTrace();
						// System.out.println("Error parsing transBuy, transSell, or selectedDate in retrievePortfolioOnADate");
						continue;
					}
					
					
					// if date falls outside of buy to sell date range
					if(selectedDate.before(transBuyDate) || selectedDate.after(transSellDate)) {
						continue; // skip this db entry, not on valid date
					}
					
					//Edit: removed the previous code checking for "bought" and "sold"
					
					
					// example rs returned: [id, user_id, "NTNX", 7, "02-01-2020", "03-05-2020"]
					// parse string for stock name and quantity
					String stockName = rs.getString(3);
					int stockQuantInt = rs.getInt(4);
					
					//If stock already in the map, increment
					if(hmap.containsKey(stockName)) {
						hmap.replace(stockName, hmap.get(stockName) + stockQuantInt);
					}
					else {
						//Else, create a new entry
						hmap.put(stockName, stockQuantInt);
					}
					
				} // end while
	        } catch (SQLException e) {
	        	// System.out.println("Error querying stock data from DB when retrieving current portfolio.");
	        	e.printStackTrace();
	        } finally {
	            try {
	                if(con != null) {
	                    con.close();
	                }
	            } catch (SQLException ex) {
	                // System.out.println(ex.getMessage());
	            }
	        }
		} // end if con != null
		
		// traverse hashmap
		for(Map.Entry<String, Integer> mapElement : hmap.entrySet()) { 
			// get stock info from map
            String stockName = (String)mapElement.getKey(); 
            int stockQuant = (int)mapElement.getValue(); 
  
            if(stockQuant != 0) {
	            // add current stock to portfolio
	            ArrayList<String> curStock = new ArrayList<String>(
	    				Arrays.asList(stockName, String.valueOf(stockQuant)));
	            portfolio.add(curStock);
            }
        } 
		
		return portfolio;
	}
	
	/*
	 * Function #5: gets what your portfolio is worth right now. We basically call
	 * the above function which is Function 3.
	 * Then you call the Api.java function getPriceOf() to determine what each stock 
	 * you currently hold is worth. This function might be a little tricky.
	 * 
	 * Output: "$1,077.45"
	 * 
	 * parameters: user_id
	 * returns: current value of portfolio
	 */
	
	public static String getCurrentPortfolioValue(int userId)
	{
		// Edit: no changes made
		ArrayList<ArrayList<String>> portfolio = retrieveCurrentPortfolio(userId);
		double portfolioVal = 0;
		
		// example portfolio value: { ["Stock", "Quantity"], ["NTNX", "3"], ["SLSF", "4"] }
		
		// starting at index 1, ignoring header strings
		for(int i = 1; i < portfolio.size(); i++) {
			// get current stock name and quantity
			String stockName = portfolio.get(i).get(0);
			int stockQuant = Integer.parseInt(portfolio.get(i).get(1));
			
			// api call to get price of stock
			double stockVal = 0;
			try {
				stockVal = Double.parseDouble(Api.getCurrentPriceOf(stockName));
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			// update running total of portfolio value
			portfolioVal += stockVal * stockQuant;
		}
		return String.valueOf(portfolioVal);
	}
	
	/*
	 * Function #6: gets the historical value of the portfolio which will be shown on
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
	public static ArrayList<ArrayList<String>> getFullLineForPortfolio(int userId)
	{
		//Edit: now we don't need to consider "bought" and "sold". Not sure about this one!
		Date start = null, end = null;
		
		// init portfolio
		ArrayList<ArrayList<String>> portfolio = new ArrayList<ArrayList<String>>();
		ArrayList<String> header = new ArrayList<String>(
				Arrays.asList("Date", "Value"));
		portfolio.add(header);
		
		// connect to mysql
		JDBC db = new JDBC();
		Connection con = db.connectDB("com.mysql.cj.jdbc.Driver", "jdbc:mysql://remotemysql.com:3306/DT6BLiMGub","DT6BLiMGub","W1B4BiSiHP");
		if(con != null) {
			try {
				// query stocks table for user id
				PreparedStatement ps = con.prepareStatement("SELECT * FROM stocks_new WHERE user_id = ?");
				ps.setInt(1, userId);
				ResultSet rs = ps.executeQuery();
				
				String transDateOfPurchase = "";
				String transDateOfSelling = "";
				// example rs returned: [id, user_id, "NTNX", 7, "02-01-2020", "03-05-2020"]

	
				// while there are stocks in the portfolio, find start and end dates
				while(rs.next()) {
					// example rs returned: [id, user_id, "bought", "NTNX", 7, "02-01-2020"]
					transDateOfPurchase = rs.getString(5);
					transDateOfSelling = rs.getString(6);
					// System.out.println("transaction date purchase: " + transDateOfPurchase + ", selling: " + transDateOfSelling);
					if(start == null && end == null) { // need to init start and end dates
						start = new SimpleDateFormat("MM-dd-yyyy").parse(transDateOfPurchase);
						end = new SimpleDateFormat("MM-dd-yyyy").parse(transDateOfSelling);
						
					} else {
						Date transBuy = new SimpleDateFormat("MM-dd-yyyy").parse(transDateOfPurchase);
						Date transSell = new SimpleDateFormat("MM-dd-yyyy").parse(transDateOfSelling);
						// System.out.println("transBuy: " + transBuy.toString() + ", transSell: " + transSell.toString());
						//earlier buy date
						if(transBuy.before(start)) {
							start = transBuy;
						}
						
						//later sell date
						if(transSell.after(end)) {
							end = transSell;
						}
					}
				}
	        } catch (SQLException e) {
	        	// System.out.println("Error querying stock data from DB when retrieving current portfolio.");
	        	e.printStackTrace();
	        } catch (ParseException e) {
	        	// System.out.println("Error parsing date.");
				e.printStackTrace();
			} finally {
	            try {
	                if(con != null) {
	                    con.close();
	                }
	            } catch (SQLException ex) {
	                // System.out.println(ex.getMessage());
	            }
	        }
		} // end if con != null
		
		// if no stocks in portfolio, return empty portfolio
		if(start == null || end == null) {
			return portfolio;
		}
		
		// loop through dates to calculate portfolio values
		LocalDate startDate = start.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate endDate = end.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		// System.out.println("start: " + start.toString());
		// System.out.println("end: " + end.toString());
		for(LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");  
		    String dateStr = formatter.format(date);  
		    String portfolioVal = getPortfolioValueOnADate(userId, dateStr);
		    
		    // add current date/value to portfolio
            ArrayList<String> curVal = new ArrayList<String>(
    				Arrays.asList(dateStr, portfolioVal));
            portfolio.add(curVal);
		}
		
		return portfolio;
	}
	
	/*
	 * Function #7: gets the historical value of a portfolio but you can specify
	 * a start and end date. Pretty much you just call Function 6 and then
	 * remove all the entries where the date falls out of the range.
	 * 
	 * parameters: user_id, start, end
	 * returns: ArrayArrayList<ArrayArrayList<String> > basically a n x 2 array
	 */
	//Edit: no changes
	public static ArrayList<ArrayList<String>> getLineForPortfolioWithDateRange(int userId, String start, String end)
	{
		ArrayList<ArrayList<String>> portfolioFull = getFullLineForPortfolio(userId);
		ArrayList<ArrayList<String>> portfolioRanged = new ArrayList<ArrayList<String>>();
		portfolioRanged.add(portfolioFull.get(0)); // add header: ["Date", "Value"]
		
		// System.out.println("full line with date range, start: " + start + " end: " + end);
		try {
			// parse date strings as Dates
			Date startDate = new SimpleDateFormat("MM-dd-yyyy").parse(start);
			Date endDate = new SimpleDateFormat("MM-dd-yyyy").parse(end); 
			
			// starting at index 1, ignoring header strings, iterate through whole portfolio
			for(int i = 1; i < portfolioFull.size(); i++) {
				String date = portfolioFull.get(i).get(0);
				Date curDate = new SimpleDateFormat("MM-dd-yyyy").parse(date);
				
				// if date is within range: if start <= date <= end
				if(!startDate.after(curDate) && !endDate.before(curDate)) {
					// add to portfolio for ranged dates
					portfolioRanged.add(portfolioFull.get(i));
				}
			}
		} catch (ParseException e) {
			// System.out.println("Error parsing dates");
			e.printStackTrace();
		} 
		
		return portfolioRanged;
	}
	
	
	/*
	 * Function #8: get the portfolio value on a single date. Let's say today was "date",
	 * based on the bought/sold transactions that have happened until now, what is my
	 * portfolio worth today. Go through the database, look for any transactions that
	 * may have happened before this date and just collect them for now. Then go through
	 * that ArrayList and do some calculations to see what stocks you have now. Then call
	 * the Api.java getPriceOf() to determine what each stock you hold is worth.
	 * You might want to use Function 5 as a guide since it is similar.
	 * Important, but tricky function.
	 * 
	 * Output: "$1,077.45"
	 * 
	 * parameters: user_id, date (there might not be any transactions that happened on this date)
	 * returns: value of portfolio on that date
	 */
	public static String getPortfolioValueOnADate(int userId, String date)
	{
		ArrayList<ArrayList<String>> portfolio = retrievePortfolioOnADate(userId, date);
		double portfolioVal = 0;
		
		//There is no data on the stock price for this date
		if(portfolio.size() == 1) {
			return "NULL";
		}
		
		// example portfolio value: { ["Stock", "Quantity"], ["NTNX", "3"], ["SLSF", "4"] }
		
		// starting at index 1, ignoring header strings
		for(int i = 1; i < portfolio.size(); i++) {
			// get current stock name and quantity
			String stockName = portfolio.get(i).get(0);
			int stockQuant = Integer.parseInt(portfolio.get(i).get(1));
			
			// api call to get price of stock
			double stockVal = 0;
			try {
				//Get the price of the stock on a specific date
				ArrayList<ArrayList<String>> temp = Api.getOneLineWithDateRange(stockName, date, date);
				
				//NOTE: This might not be right!
				if(temp.size() >= 2 && temp.get(1).size() >= 2) {
					stockVal = Double.parseDouble(temp.get(1).get(1));
				}
				else {
					stockVal = Double.parseDouble(Api.getCurrentPriceOf(stockName));
				}
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			// update running total of portfolio value
			portfolioVal += stockVal * stockQuant;
		}
		return String.valueOf(portfolioVal);
	}
	
	/*
	 * Function #:
	 * 
	 * parameters:
	 * returns:
	 */
}