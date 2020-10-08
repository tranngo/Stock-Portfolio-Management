package csci310;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Iterator;

import com.google.gson.Gson;

import java.util.ArrayList;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.Interval;

public class Api {
	
	public static boolean isNumeric(String str) { 
		  try {  
		    Double.parseDouble(str);  
		    return true;
		  } catch(NumberFormatException e){  
		    return false;  
		  }  
	}

	
	/*
	 * parameters: stock ticker
	 * returns: current price of stock as string (CAN ALSO BE BIG DECIMAL)
	 */
	public static String getCurrentPriceOf(String name) throws IOException {
		Stock stock = YahooFinance.get(name);
		BigDecimal price = stock.getQuote(true).getPrice();
		return price.toString();
	}

	
	/*
	 * parameters: stock ticker
	 * returns: a ArrayList of the stock's daily values for the past year as a string
	 */
	public String getHistoricalPricesOf(String name) throws IOException {
		Stock stock = YahooFinance.get(name);
		return stock.getHistory(Interval.MONTHLY).toString();
	}

	
	/*
	 * parameters: stock ticker, start date, end date, interval 
	 * returns: a ArrayList of the stock's values over an interval as a string
	 */
	public String getPriceOfStockOnSpecificDate(String name, Calendar f, Calendar t, Interval i) throws IOException {	
		Stock stock = YahooFinance.get(name);
		return stock.getHistory(f, t, i).toString();
	}


	/* New Api.java functions from Sprint 2 */
	
	/*
	 * Function #1: check if a stock is actually a valid one (not like "JKJK")
	 * There's a special case where if the stock is named PORTFOLIO_somenumber
	 * then that is actually considered a valid stock.
	 * 
	 * parameters: name of a stock as a string
	 * returns: true or false, whether it is valid
	 */
	public static boolean isValidStock(String name) throws IOException
	{
		Stock stock = YahooFinance.get(name);
		try {
			return stock.isValid();
		}
		catch (NullPointerException e) {
			System.out.println("NullPointerException Caught"); 
		}
		return false;
	}
	
	/*
	 * Function #2: take a ArrayList<ArrayList<String> > and turn it into JSON using the GSON library
	 * The key is that the labels should remain as Strings, but the numbers should be doubles.
	 * Pretty much you ignore the first row and first column, then you turn the remaining 
	 * entries from strings to doubles.
	 * 
	 * Special Case: Handle "NULL" strings
	 * And Also: convert the date strings to be in MM-DD-YYYY format, right now the API
	 * returns them in YYYY-MM-DD format
	 * 
	 * Here's an example:
	 * ['Date', 'Stock 1', 'Stock 2']			  ['Date', 'Stock 1', 'Stock 2']
	 * ['10/1', '112.50', '37.50']      ------->  ['10/1', 112.50, 37.50]
	 * ['10/2', '115.90', '40.35']				  ['10/2', 115.90, 40.35]
	 * All entries are strings                     Now you have some strings and some doubles
	 * 
	 * parameters: ArrayList<ArrayList<String> > like the first part of the example
	 * returns: a JSON string that resembles the second part of the example
	 */
	public static String datasetToJSON(ArrayList<ArrayList<String>> dataset)
	{
		Gson gson = new Gson();
		String result = "[" + gson.toJson(dataset.get(0)) + ",";
		int datasetSize = dataset.size();
		for (int i = 1; i < datasetSize; i++) {
			result += "[";
			int firstDatasetSize = dataset.get(0).size();
			
			//Go over the dataset entry
			for (int j = 0; j < firstDatasetSize; j++) {
				if(isNumeric(dataset.get(i).get(j))){
	//				if (j == dataset.get(0).size()-1) {
						result += gson.toJson(Double.parseDouble(dataset.get(i).get(j)));
	//				}
	/*	NOTE: Fix this			else {
						result += gson.toJson(Double.parseDouble(dataset.get(i).get(j))) + ",";
					}           */
				}
				else {
					result += gson.toJson(dataset.get(i).get(j)) + ",";
				}
			}
			//For the last entry
			if (i == dataset.size()-1) {
				result += "]";
			}
			else {
				//For all other entries
				result += "],";
			}
		}
		return result + "]";
	}
	
	/*
	 * Function #3: Parses the long string that the Yahoo Finance API provides and turns it
	 * into a usable data structure like a n x 2 ArrayList
	 * 
	 * Input: "TSLA@2020-01-01: 84.342003-130.600006, 84.900002->130.113998 (130.113998), "
	 *			+ "TSLA@2020-02-01: 122.304001-193.798004, 134.738007->133.598007 (133.598007), "
	 * Result: n x 2 ArrayList
	 * 
	 * Also: convert the date strings to be in MM-DD-YYYY format, right now the API
	 * returns them in YYYY-MM-DD format
	 * 
	 * Output:
	 * ["Date", "NTNX"]
	 * ["01-30-2020", "130.11"]
	 * ["02-02-2020", "133.59"]
	 * 
	 * parameters: String for the stock name
	 * returns: ArrayList<ArrayList<String> > basically a n x 2 array
	 */
	public static ArrayList<ArrayList<String>> fetchAndParse(String stock)
	{
		return null;
	}
	
	/*
	 * Function #4: retrieves all the historical stock prices for a single stock ("NTNX").
	 * First calls Function 1, to check if the stock is valid.
	 * Then uses Function 3 (fetchAndParse) to do most of the heavy lifting.
	 * Special case: a stock can be "PORTFOLIO_userid" in that case you retrieve
	 * the historical value of the portfolio. Don't use the Api.java function for that
	 * there is a special function that will be written in Portfolio.java to make this easy
	 * for us to do.
	 * 
	 * 
	 * The output is like this, let's say "NTNX" started in 10/2/2014 and now it is 10/5/2020
	 * ["Date", "NTNX"]
	 * ["01-30-2020", "130.11"]
	 * ["02-02-2020", "133.59"]
	 * 
	 * parameters: String for the stock name
	 * returns: ArrayList<ArrayList<String> > basically a n x 2 array
	 */
	public static ArrayList<ArrayList<String>> getOneLineAllData(String stock)
	{
		return null;
	}
	
	/*
	 * Function #5: retrieves all historical prices for a ArrayList of stocks ["NTNX", "CRM", "TMZ"]
	 * First calls Function 1, to validate the ArrayList. If any one of them is invalid,
	 * then print which one is invalid, but just remove the invalid one from the ArrayList
	 * and continue.
	 * Merge resolution: if let's say for one stock we only have 3 months of historical
	 * data and the other one we have 1 year of historical data. For stock 1,
	 * in the 9 months where we don't have data just put this string "NULL" for now.
	 * 
	 * Special case: a stock can be "PORTFOLIO_userid" in that case you retrieve
	 * the historical value of the portfolio. Don't use the Api.java function for that
	 * there is a special function that will be written in Portfolio.java to make this easy
	 * for us to do.
	 * 
	 * ["Date", "NTNX", "SLFS", "PORTFOLIO_37"]
	 * ["01-30-2020", "130.11", "NULL", "NULL"]
	 * ["02-02-2020", "133.59", "NULL", "6,765.94"]
	 * ["03-03-2020", "138.79", "91.2", "6,765.94"]
	 * ["04-04-2020", "139.99", "91.2", "6,765.94"]
	 * 
	 * parameters: ArrayList<String> a ArrayList of stocks and maybe a PORTFOLIO_userid in that ArrayList
	 * returns: ArrayList<ArrayList<String> > basically a n x m array
	 */
	public static ArrayList<ArrayList<String>> getMultipleLinesAllData(ArrayList<String> stocks)
	{
		return null;
	}
	
	/*
	 * Function #6: Works exactly like Function 4 but you specify a date range.
	 * So this function essentially retrieves all stock data for a stock like "NTNX"
	 * between a start date and an end date.
	 * This is an easy function to do, just call Function 4 and then iterate through
	 * the 2xn array, delete any rows that don't fall within the date range.
	 * 
	 * The output looks pretty much like Function 4
	 * ["Date", "NTNX"]
	 * ["01-30-2020", "130.11"]
	 * ["02-02-2020", "133.59"]
	 * 
	 * parameters: String for stock, String for start date, String for end date
	 * returns: ArrayList<ArrayList<String> > basically a n x 2 array
	 */
	public static ArrayList<ArrayList<String>> getOneLineWithDateRange(String stock, String start, String end)
	{
		return null;
	}
	
	/*
	 * Function #7: Works exactly like Function 5 except you specify a date range.
	 * We are retrieving stock data for a ArrayList of stocks ["NTNX", "CRM", "TMZ"] but
	 * only between a start date and an end date that we take as parameters.
	 * This should be an easy function to do, just call Function 5 and iterate through
	 * the nxm array, delete the rows which don't fall within the date range.
	 * 
	 * The output looks pretty much like Function 5
	 * 
	 * ["Date", "NTNX", "SLFS", "PORTFOLIO_37"]
	 * ["01-30-2020", "130.11", "NULL", "NULL"]
	 * ["02-02-2020", "133.59", "NULL", "6,765.94"]
	 * ["03-03-2020", "138.79", "91.2", "6,765.94"]
	 * ["04-04-2020", "139.99", "91.2", "6,765.94"]
	 * 
	 * parameters: ArrayList<String> for stocks, String for start date, String for end date
	 * returns: ArrayList<ArrayList<String> > basically a n x m array
	 */
	public static ArrayList<ArrayList<String>> getMultipleLinesWithDateRange(ArrayList<String> stocks, String start, String end) throws IOException
	{
		
				//TO DO: make sure the "NULL" entries come properly from Function 6!
		
		
		//stocks = ["NTNX", "SLFS", "PORTFOLIO_37"]
		//start = 01-30-2020
		//end= 02-05-2020
		
		//Go through each stock and check if valid
	/*	Iterator<String> i = stocks.iterator();
		while(i.hasNext())
		{
			String stock = i.next();
			//An invalid stock is found
			if(isValidStock(stock) == false)
			{
				System.out.println("Warning: " + stock + " is not a valid stock!");
				i.remove();
			}
		}
		
		//Just to check that invalid stocks are removed
		System.out.println("Hopefully invalid stocks are removed");
		System.out.println("Stocks left in list are");
		for(String stock : stocks)
		{
			System.out.print(stock + " ");
		}
		
		//Retrieve each stock's values using function 6
		ArrayList<ArrayList<String>> finalDataset = null;
		for(String stock : stocks)
		{
			//Special case if the stock is PORTFOLIO_userid
			if(stock.startsWith("PORTFOLIO_"))
			{
				//Get the user id
				String user_id = stock.substring(10);
				System.out.println("PORTFOLIO_ is for user id: " + user_id);
				int id = Integer.parseInt(user_id);
				
				//Call the portfolio.java function
				ArrayList<ArrayList<String>> oneTable = Portfolio.getLineForPortfolioWithDateRange(id, start, end);
			
				if(finalDataset == null) // for first column
				{
					finalDataset = oneTable;
				}
				else // for other columns
				{
					//We want to drop the whole first column of oneTabel, because that has the dates
					//We just need the second column which has the stock prices
					for(ArrayList<String> row : oneTable)
					{
						row.remove(0);
					}
					
					//Next we want to append to the finalDataset
					for(int p = 0; p < oneTable.size(); p++)
					{
						String entryInColumn = oneTable.get(p).get(0); // ["NTNX"] , ["130.11"], ["133.59"]
						finalDataset.get(p).add(entryInColumn);
					}
				}
			}
			else 
			{
				ArrayList<ArrayList<String>> oneTable = getOneLineWithDateRange(stock, start, end);
				if(finalDataset == null) // for first column
				{
					finalDataset = oneTable;
				}
				else // for other columns
				{
					//We want to drop the whole first column of oneTabel, because that has the dates
					//We just need the second column which has the stock prices
					for(ArrayList<String> row : oneTable)
					{
						row.remove(0);
					}
					
					//Next we want to append to the finalDataset
					for(int p = 0; p < oneTable.size(); p++)
					{
						String entryInColumn = oneTable.get(p).get(0); // ["NTNX"] , ["130.11"], ["133.59"]
						finalDataset.get(p).add(entryInColumn);
					}
				}
			}
		}
		
		return finalDataset; */
		return null;
	}
	
	/*
	 * Function #:
	 * 
	 * parameters:
	 * returns:
	 */
}
