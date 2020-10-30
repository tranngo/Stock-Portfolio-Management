package csci310;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
	
	//takes in a string double value and rounds it
	public static String roundDouble(String value1) {
		Double value = Double.parseDouble(value1);
		String v = "0";
	    if(value == (double) Math.round(value)){
	    	v = String.format("%.1f", value);
	    }
	    else{
	        v = String.format("%.2f", value);
	    }
		return v;
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
	 * returns: a ArrayList of the stock's monthly values for the past year as a string
	 */
	public static String getHistoricalPricesOf(String name) throws IOException {
		Stock stock = YahooFinance.get(name);
		System.out.println(stock.getHistory(Interval.MONTHLY).toString());
		return stock.getHistory(Interval.MONTHLY).toString();
	}
	
	/*
	 * parameters: stock ticker
	 * returns: a ArrayList of the stock's daily values for the past year as a string
	 */
	public static String getDailyHistoricalPricesOf(String name) throws IOException {
		Stock stock = YahooFinance.get(name);
		return stock.getHistory(Interval.DAILY).toString();
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
		if(name.startsWith("PORTFOLIO_")) {
			return true;
//			String[] strings = name.split("_");
//			return strings.length == 2; // make sure name matches PORTFOLIO_userid
		}
		
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
	 * And Also: convert the date strings to be in MM-dd-yyyy format, right now the API
	 * returns them in yyyy-MM-DD format
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
		for (int i = 1; i < dataset.size(); i++) {
			result += "[";
			for (int j = 0; j < dataset.get(0).size(); j++) {
				if(dataset.get(i).get(j) != null && isNumeric(dataset.get(i).get(j))){
					if (j == dataset.get(0).size()-1) {
						result += gson.toJson(Double.parseDouble(dataset.get(i).get(j)));
					}
					else {
						result += gson.toJson(Double.parseDouble(dataset.get(i).get(j))) + ",";
					}           
				}
				else {
					if(dataset.get(i).get(j).equals("NULL")) {
						result += "null";
						if(j != dataset.get(i).size()-1) {
							result += ",";
						}
					}
					else {
						result += gson.toJson(dataset.get(i).get(j)) + ",";
					}
				}
			}
			if (i == dataset.size()-1) {
				result += "]";
			}
			else {
				result += "],";
			}
		}
		System.out.println("resul in datasetToJSON: " + result + "]");
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
	 * Also: convert the date strings to be in MM-dd-yyyy format, right now the API
	 * returns them in yyyy-MM-DD format
	 * 
	 * Output:
	 * ["Date", "NTNX"]
	 * ["01-30-2020", "130.11"]
	 * ["02-02-2020", "133.59"]
	 * 
	 * parameters: String for the stock name
	 * returns: ArrayList<ArrayList<String> > basically a n x 2 array
	 */
	public static ArrayList<ArrayList<String>> fetchAndParse(String name, String s) throws IOException
	{
		ArrayList<ArrayList<String>> output = new ArrayList<ArrayList<String>>();
		ArrayList<String> first_line = new ArrayList<String>();
		
		//add date and the name of the stock to the first line
		first_line.add("Date");
		first_line.add(name);
		
		//add the first line to the output array
		output.add(first_line);
		
		/*call split() based on commas in the string. 
		 * Each individual stock price will take 2 indexes 
		 * The first index will be used to get the date
		 * The second index will be used to get the price*/
		String[] myStrings = s.split(",");
		
		//loop through the substrings from the split and add appropriate data to the output
		for (int i = 0; i < myStrings.length; i+=2) {
			//new line for the array
			ArrayList<String> new_line = new ArrayList<String>();
			
			//capture the first index and the incorrectly formatted date
			String str_date = myStrings[i];
			str_date = str_date.substring(str_date.indexOf("@")+1,str_date.indexOf(":"));
			
			//reformat the date into proper order and add to the new line
			String[] dates = str_date.split("-");
			String new_date = dates[1] + "-" + dates[2] + "-" + dates[0];
			new_line.add(new_date);
			
			//capture the second index and gather it's price
			String str = myStrings[i+1];
			String price = str.substring(str.indexOf("(")+1,str.indexOf(")"));
			//round the value and add to the new line
			new_line.add(roundDouble(price));
			
			//add to the output array
			output.add(new_line);
		}
		
		return output;
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
	public static ArrayList<ArrayList<String>> getOneLineAllData(String name) throws IOException
	{
		
		if(name.startsWith("PORTFOLIO_"))
		{
			ArrayList<ArrayList<String>> dataset = new ArrayList<ArrayList<String>>();
			//This is not a stock, use Portfolio.java helper function
			String idAsString = name.substring(10);
			int portfolio_number = Integer.parseInt(idAsString);
			System.out.println("In Api.java, getOneLineAllData(), Portfolio " + portfolio_number + " was requested!");
			
			//Portfolio.java helper function to retrieve portfolio with date range
			dataset = Portfolio.getFullLineForPortfolio(portfolio_number);
			
			System.out.println("TODO: getOneLineAllData, once Portfolio is implemented there should be no errors");
			return dataset;
		}
		
		
		String s = getDailyHistoricalPricesOf(name);
		return fetchAndParse(name, s);
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
		ArrayList<ArrayList<String>> dataset = new ArrayList<ArrayList<String>>();
		
		//First, remove any invalid stocks
		Iterator<String> i = stocks.iterator();
		while(i.hasNext())
		{
			String stock = i.next();
			//An invalid stock is found
			try {
				if(isValidStock(stock) == false)
				{
					System.out.println("Warning: " + stock + " is not a valid stock!");
					i.remove();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		//Just to check that invalid stocks are removed
		System.out.println("Hopefully invalid stocks are removed");
		System.out.println("Stocks left in list are");
		for(String stock : stocks)
		{
			System.out.print(stock + " ");
		}
		System.out.println("");
		
		//Second, we have to go through each of the stocks and determine which one has the
		//earliest start date and which has the latest end date
		String earliestStartDate = "";
		String latestEndDate = "";
		
		//Set stocks[0] as the default
		ArrayList<ArrayList<String>> firstLine = new ArrayList<ArrayList<String>>();
		try {
			firstLine = getOneLineAllData(stocks.get(0));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			System.out.println("Error in Function 5, getMultipleLinesAllData");
			return dataset;
		}
		
		if(firstLine.size() >= 3)
		{
			earliestStartDate = firstLine.get(1).get(0);
			latestEndDate = firstLine.get(firstLine.size()-1).get(0);
		}
		else
		{
			System.out.println("Strange error in getMultipleLinesAllData, not enough rows");
		}
		
		for(int j = 1; j < stocks.size(); j++)
		{
			ArrayList<ArrayList<String>> temp;
			try {
				temp = getOneLineAllData(stocks.get(j));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				System.out.println("getMultipleLinesAllData has an error yo");
				return dataset;
			}

			if(stocks.get(j).startsWith("PORTFOLIO_"))
			{
				System.out.println("ERROR, FIX LATER: getMultipleLinesAllData, since Portfolio is not fully implemented, we are messing up one part of this function");
				continue;
			}
			
			String tempStartDate = temp.get(1).get(0);
			String tempEndDate = temp.get(temp.size()-1).get(0);
			System.out.println("j=" + j + " Start: " + tempStartDate + " and End: " + tempEndDate);
			
			SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy");

			try {
				Calendar date1 = Calendar.getInstance();
				date1.setTime(format.parse(earliestStartDate));
				Calendar date2 = Calendar.getInstance();
				date2.setTime(format.parse(tempStartDate));
				if(date1.before(date2))
				{
					System.out.println("Earlier start date found!");
					earliestStartDate = tempStartDate;
				}
				
				Calendar date3 = Calendar.getInstance();
				date3.setTime(format.parse(latestEndDate));
				Calendar date4 = Calendar.getInstance();
				date4.setTime(format.parse(tempEndDate));
				if(date3.after(date4))
				{
					System.out.println("Later end date found!");
					latestEndDate = tempEndDate;
				}
				
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			
		}
		
		System.out.println("Starting to gather stock lines");
		
		//Now we have the date range for our dataset, start -> end
		//We can use Function 6, to fill in the "NULL" and get us what we need
		for(int k = 0; k < stocks.size(); k++)
		{
			String stock = stocks.get(k);
			if(stock.startsWith("PORTFOLIO_"))
			{
				System.out.println("ERROR, FIX LATER: getMultipleLinesAllData, since Portfolio is not fully implemented, we are messing up one part of this function");
				continue;
			}
			
			
			ArrayList<ArrayList<String>> temp = getOneLineWithDateRange(stock, earliestStartDate, latestEndDate);
			if(k == 0)
			{
				dataset = temp;
			}
			else
			{
				//We want to append to dataset, start from row index of 1
				dataset.get(0).add(stock);
				for(int row = 1; row < dataset.size(); row++)
				{
					dataset.get(row).add( temp.get(row).get(1)  );
				}
			}
		}
		
		
		return dataset;
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
		//TO DO: make sure the "NULL" entries come properly
		
		
		
				ArrayList<ArrayList<String>> dataset = new ArrayList<ArrayList<String>>();
				
				//First, check if the stock is valid
				boolean valid = false;
				try {
					valid = isValidStock(stock);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					System.out.println("IOException in getOneLineWithDateRange, terminating function!");
					e.printStackTrace();
					return dataset;
				}
				
				if(valid == false)
				{
					System.out.println(stock + " is invalid, returing empty dataset!");
					return dataset;
				}
				
				//Second, retrieve the data for that line
				if(stock.startsWith("PORTFOLIO_"))
				{
					//This is not a stock, use Portfolio.java helper function
					String idAsString = stock.substring(10);
					int portfolio_number = Integer.parseInt(idAsString);
					System.out.println("In Api.java, getOneLineWithDateRange(), Portfolio " + portfolio_number + " was requested!");
					
					//Portfolio.java helper function to retrieve portfolio with date range
					dataset = Portfolio.getLineForPortfolioWithDateRange(portfolio_number, start, end);
				}
				else
				{
					//This is a stock, use Function 4 and delete rows that fall outside date range
					
					//First, call Function 4
					try {
						dataset = getOneLineAllData(stock);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						System.out.println("Error in getOneLineWithDateRange");
						return dataset;
					}
					
					//Next, filter out rows that fall outside the start and end date
					Iterator<ArrayList<String>> i = dataset.iterator();
					SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy");
					Calendar startDate = Calendar.getInstance();
					Calendar endDate = Calendar.getInstance();
					try {
						startDate.setTime(format.parse(start));
						endDate.setTime(format.parse(end));
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						System.out.println("Error parsing start and end date in getOneLineWithDateRange");
						return dataset;
					}
					
					Calendar date = Calendar.getInstance();
					
					while(i.hasNext())
					{
						ArrayList<String> row = i.next();
						String dateStr = row.get(0); //get the date
						try {
//							System.out.println("dateStr: " + dateStr);
							date.setTime(format.parse(dateStr));
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							//e.printStackTrace();
							System.out.println("Ignore this, it's meant to happen: Slight error parsing row's date in getOneLineWithDateRange");
							continue;
						}
						
						//This date is before the specified start date, filter it out
						if(date.before(startDate))
						{
							//System.out.println(dateStr + " is less than " + start + " removing it!* itr");
							i.remove();
						}
						else if(date.after(endDate))
						{
							//This date is after the specified end date, filter it out
							//System.out.println(dateStr + " is bigger than " + end + " removing it!* itr");
							i.remove();
						}
					}
					
					
					//As a final measure, we want to add "NULL" entries to pad up the array
					
					//Let's say there was no data found in this range, return dataset
					if(dataset.size() == 1) {
						return dataset;
					}
					String firstDateInDataset = dataset.get(1).get(0);
					try {
						date.setTime(format.parse(firstDateInDataset));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						System.out.println("Error parsing row's date in getOneLineWithDateRange");
						return dataset;
					}
					
					try {
						startDate.setTime(format.parse(start));
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					if(date.after(startDate)) {
						System.out.println("PRE-PADDING NULLs");
						while(date.after(startDate))
						{
							ArrayList<String> nullRow = new ArrayList<String>();
							Calendar cal = date;
							int howManyBefore = -1;
							cal.add(Calendar.DATE, howManyBefore);
							Date oneBeforeDate = cal.getTime();
							String oneBeforeString = format.format(oneBeforeDate);
							
							nullRow.add(oneBeforeString);
							nullRow.add("NULL");
							dataset.add(1, nullRow);
							
							//Update date and check again
							firstDateInDataset = dataset.get(1).get(0);
							try {
								date.setTime(format.parse(firstDateInDataset));
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
								System.out.println("Error parsing row's date in getOneLineWithDateRange");
								return dataset;
							}
						}
					}
					else {
						System.out.println("No need to pre pad nulls");
					}
					
					
				}
				
			

			
				return dataset;
	}
	

	
	
	
	// ======================   Functions 1-6 are all set
	
	
	
	
	
	
	
	
	
	
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
		
		//stocks = ["NTNX", "SLFS", "PORTFOLIO_37"]
		//start = 01-30-2020
		//end= 02-05-2020
		
		ArrayList<ArrayList<String>> dataset = new ArrayList<ArrayList<String>>();
		
		//First, remove any invalid stocks
		Iterator<String> i = stocks.iterator();
		while(i.hasNext())
		{
			String stock = i.next();
			//An invalid stock is found
			try {
				if(isValidStock(stock) == false)
				{
					System.out.println("Warning: " + stock + " is not a valid stock!");
					i.remove();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		//Just to check that invalid stocks are removed
		System.out.println("Hopefully invalid stocks are removed");
		System.out.println("Stocks left in list are");
		for(String stock : stocks)
		{
			System.out.print(stock + " ");
		}
		System.out.println("");
		
		
		System.out.println("Starting to gather stock lines");
		
		//We can use Function 6, to fill in the "NULL" and get us what we need
		for(int k = 0; k < stocks.size(); k++)
		{
			String stock = stocks.get(k);
//			ArrayList<ArrayList<String>> portfolioStocks;
			ArrayList<ArrayList<String>> temp;
			if(stock.startsWith("PORTFOLIO_"))
			{
				String[] strings = stock.split("_"); // {"PORTFOLIO_", userid}
				int userId = Integer.parseInt(strings[1]);
				System.out.println("userid: " + userId);
				temp = Portfolio.getLineForPortfolioWithDateRange(userId, start, end);
//				System.out.println("ERROR, FIX LATER: getMultipleLinesAllData, since Portfolio is not fully implemented, we are messing up one part of this function");
//				continue;
				System.out.println("portfolio line");
				for(int a = 0; a < temp.size(); a++) {
					for(int b = 0; b < temp.get(a).size(); b++) {
						System.out.print(temp.get(a).get(b) + " ");
					}
					System.out.println("");
				}
			} else {
				temp = getOneLineWithDateRange(stock, start, end);
			}
			if(k == 0)
			{
				dataset = temp;
			}
			else
			{
				//We want to append to dataset, start from row index of 1
				dataset.get(0).add(stock);
				boolean canIndexTemp = false;
				int tempIndex = 1;
				for(int row = 1; row < dataset.size(); row++)
				{
					if(stock.startsWith("PORTFOLIO_") && temp.size() > 1) { // if portfolio stocks, and portfolio is not empty
						if(!canIndexTemp) { // NOT safe to index temp
//							for(int j = 0; j < temp.size(); j++) {
								if(dataset.get(row).get(0).equals(temp.get(tempIndex).get(0))) { // if dates match
//									tempIndex = 0; // save valid 
									canIndexTemp = true;
									System.out.println("found matching date: " + temp.get(tempIndex).get(0));
									dataset.get(row).add( temp.get(tempIndex++).get(1) );
//									break;
								} else {
									dataset.get(row).add("0.00");
//									dataset.get(row).add(null);
								}
//							}
								
						} else { // can index temp to populate dataset
							dataset.get(row).add( temp.get(tempIndex++).get(1) );
						}
					} else {
//					System.out.println("dataset size: " + dataset.size() + ", temp size: " + temp.size());
						
						//THIS IF STATEMENT MIGHT BE A PROBLEM IF PORTFOLIO_7 NOT SHOWING UP
						if(temp.size() > row && temp.get(row).size() >= 1) {
							dataset.get(row).add( temp.get(row).get(1) );
						}
					}
				}
			}
			System.out.println("DATASET: ");
			for(int a = 0; a < dataset.size(); a++) {
				for(int b = 0; b < dataset.get(a).size(); b++) {
					System.out.print(dataset.get(a).get(b) + " ");
				}
				System.out.println("");
			}
		}
		
		
		return dataset;
	}
	
	/*
	 * Function #:
	 * 
	 * parameters:
	 * returns:
	 */
}