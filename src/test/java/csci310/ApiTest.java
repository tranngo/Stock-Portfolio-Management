package csci310;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Calendar;

import org.junit.BeforeClass;
import org.junit.Test;

import yahoofinance.histquotes.Interval;

public class ApiTest {
	
	private static Api api;
	
	@BeforeClass
	static public void apiSetup() {
		api = new Api();
	}

	@Test
	public void testGetCurrentPriceOf() throws IOException {
		assertEquals("incorrect current price", api.getCurrentPriceOf("TSLA"), "424.23");
	}
	
	@Test
	public void testGetHistoricalPricesOf() throws IOException {
		String s = "[TSLA@2019-10-01: 44.855999-68.167999, 48.299999->62.984001 (62.984001), "
				+ "TSLA@2019-11-01: 61.852001-72.239998, 63.264000->65.987999 (65.987999), "
				+ "TSLA@2019-12-01: 65.449997-87.061996, 65.879997->83.666000 (83.666000), "
				+ "TSLA@2020-01-01: 84.342003-130.600006, 84.900002->130.113998 (130.113998), "
				+ "TSLA@2020-02-01: 122.304001-193.798004, 134.738007->133.598007 (133.598007), "
				+ "TSLA@2020-03-01: 70.101997-161.395996, 142.251999->104.800003 (104.800003), "
				+ "TSLA@2020-04-01: 89.279999-173.964005, 100.800003->156.376007 (156.376007), "
				+ "TSLA@2020-05-01: 136.608002-168.658005, 151.000000->167.000000 (167.000000), "
				+ "TSLA@2020-06-01: 170.820007-217.537994, 171.600006->215.962006 (215.962006), "
				+ "TSLA@2020-07-01: 216.100006-358.997986, 216.600006->286.152008 (286.152008), "
				+ "TSLA@2020-08-01: 273.000000-500.140015, 289.839996->498.320007 (498.320007), "
				+ "TSLA@2020-09-01: 329.880005-502.489990, 502.140015->424.230011 (424.230011), "
				+ "TSLA@2020-09-22: 417.600098-437.760010, 429.600006->424.230011 (424.230011)]";
		assertEquals("incorrect historical price", api.getHistoricalPricesOf("TSLA"), s);
	}
	
	@Test
	public void testGetPriceOfStockOnSpecificDate() {
		String s = "[TSLA@2020-09-18: 428.799988-451.000000, 447.940002->442.149994 (442.149994), "
				+ "TSLA@2020-09-21: 407.070007-455.679993, 453.130005->449.390015 (449.390015), "
				+ "TSLA@2020-09-22: 417.600006-437.760010, 429.600006->424.230011 (424.230011)]";
		Calendar f = Calendar.getInstance();
		f.set(Calendar.YEAR, 2020);
		f.set(Calendar.MONTH, Calendar.SEPTEMBER);
		f.set(Calendar.DATE, 17);
		
		Calendar t = Calendar.getInstance();
		f.set(Calendar.YEAR, 2020);
		f.set(Calendar.MONTH, Calendar.SEPTEMBER);
		f.set(Calendar.DATE, 18);
		
		try {
			assertEquals("incorrect price of stock on specific date", api.getPriceOfStockOnSpecificDate("TSLA", f, t, Interval.DAILY), s);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
