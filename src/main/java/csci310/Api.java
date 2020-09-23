package csci310;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Calendar;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.Interval;

public class Api {
	
	/*
	 * parameters: stock ticker
	 * returns: current price of stock as string (CAN ALSO BE BIG DECIMAL)
	 */
	public String getCurrentPriceOf(String name) throws IOException {
		String price = "";
		return price;
	}
	
	/*
	 * parameters: stock ticker
	 * returns: a list of the stock's daily values for the past year as a string
	 */
	public String getHistoricalPricesOf(String name) throws IOException {
		String prices = "";
		return prices;
	}
	
	/*
	 * parameters: stock ticker, start date, end date, interval 
	 * returns: a list of the stock's values over an interval as a string
	 */
	public String getPriceOfStockOnSpecificDate(String name, Calendar from, Calendar to, Interval interval) throws IOException {
		String price = "";
		return price;
	}


}
