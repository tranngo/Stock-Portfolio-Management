package csci310;

import java.util.ArrayList;

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
		return null;
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
		return "";
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
