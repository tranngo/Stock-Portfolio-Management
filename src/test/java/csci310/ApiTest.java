package csci310;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Calendar;

import org.junit.BeforeClass;
import org.junit.Test;

import yahoofinance.histquotes.Interval;

public class ApiTest {
	
	private static Api api;
	
	public static boolean isNumeric(String str) { 
		  try {  
		    Double.parseDouble(str);  
		    return true;
		  } catch(NumberFormatException e){  
		    return false;  
		  }  
	}
	
	@BeforeClass
	static public void apiSetup() {
		api = new Api();
	}

	@Test
	public void testGetCurrentPriceOf() throws IOException {
		assertTrue("incorrect current price", isNumeric(api.getCurrentPriceOf("TSLA")));
	}
	
	@Test
	public void testGetHistoricalPricesOf() throws IOException {
		String s = "TSLA@2020-01-01: 84.342003-130.600006, 84.900002->130.113998 (130.113998), "
				+ "TSLA@2020-02-01: 122.304001-193.798004, 134.738007->133.598007 (133.598007), "
				+ "TSLA@2020-03-01: 70.101997-161.395996, 142.251999->104.800003 (104.800003), ";
		assertTrue("incorrect historical price", api.getHistoricalPricesOf("TSLA").contains(s));
	}
	
	@Test
	public void testGetPriceOfStockOnSpecificDate() {
		String s = "[TSLA@2020-09-14: 373.299988-420.000000, 380.950012->419.619995 (419.619995)]";
		Calendar f = Calendar.getInstance();
		f.set(Calendar.YEAR, 2020);
		f.set(Calendar.MONTH, Calendar.SEPTEMBER);
		f.set(Calendar.DATE, 14);
		
		Calendar t = Calendar.getInstance();
		t.set(Calendar.YEAR, 2020);
		t.set(Calendar.MONTH, Calendar.SEPTEMBER);
		t.set(Calendar.DATE, 15);
		
		try {
			assertEquals("incorrect price of stock on specific date", api.getPriceOfStockOnSpecificDate("TSLA", f, t, Interval.DAILY), s);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
