package csci310;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class PortfolioTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testAddStock() {
		int result = Portfolio.addStock(1, "NTNX", 10, "10-05-2020");
		assertEquals(result, 1);
		
		result = Portfolio.addStock(1, "NTNX", -1, "10-05-2020");
		assertEquals(result, 0);
	}
	
	/*
	 * 
	 * ISSUE: For these test cases, the database is updated each time and the changes
	 * made by each test case will persist in the database.
	 * So we have to make sure the test cases is actually correct.
	 * Perhaps we can sell the stocks that we add to account for this.
	 */

	@Test
	public void testSellStock() {
		Portfolio.addStock(1, "NTNX", 10, "10-05-2020"); // buy 10 "NTNX" stocks
		int result = Portfolio.sellStock(1, "NTNX", 5, "10-05-2020"); //sell 5 of them
		assertEquals(result, 1);
		
		result = Portfolio.sellStock(1, "NTNX", 100, "10-05-2020"); //sell 100 of them
		assertEquals(result, 0);
	}

	@Test
	public void testRetrieveMyCurrentPortfolio() {
		Portfolio.addStock(1, "NTNX", 10, "10-05-2020"); // buy 10 "NTNX" stocks
		Portfolio.addStock(1, "JNJ", 10, "10-05-2020"); // buy 10 "JNJ" stocks
		ArrayList<ArrayList<String>> result = Portfolio.retrieveMyCurrentPortfolio(1);
		
		boolean valid = true;
		
		//Check each entry
		if(result.get(0).get(0) != "Stock")
			valid = false;
		if(result.get(0).get(1) != "Quantity")
			valid = false;
		if(result.get(1).get(0) != "NTNX")
			valid = false;	
		if(result.get(1).get(1) != "10")
			valid = false;
		if(result.get(2).get(0) != "JNJ")
			valid = false;
		if(result.get(2).get(1) != "10")
			valid = false;
		
		assertTrue(valid);
		
		//Also maybe have a test case where we then remove some stocks from the portfolio
	}

	@Test
	public void testGetCurrentPortfolioValue() {
		Portfolio.addStock(1, "NTNX", 10, "10-05-2020"); // buy 10 "NTNX" stocks
		Portfolio.addStock(1, "JNJ", 10, "10-05-2020"); // buy 10 "JNJ" stocks
		
		String result = Portfolio.getCurrentPortfolioValue(1);
		double value = Double.parseDouble(result);
		
		boolean valid = (value > 500);
		assertTrue(valid);
	}

	@Test
	public void testGetFullLineForPortfolio() {
		
		Portfolio.addStock(1, "NTNX", 10, "09-05-2020"); // buy 10 "NTNX" stocks
		Portfolio.addStock(1, "JNJ", 10, "09-07-2020"); // buy 10 "JNJ" stocks
		
		ArrayList<ArrayList<String>> result = Portfolio.getFullLineForPortfolio(1);
		
		boolean valid = true;
		
		//NOTE: This test case still has to be fixed
		//Check each entry
		if(result.get(0).get(0) != "Date")
			valid = false;
		if(result.get(0).get(1) != "Value")
			valid = false;
		if(result.get(1).get(0) != "NTNX")
			valid = false;	
		if(result.get(1).get(1) != "10")
			valid = false;
		if(result.get(2).get(0) != "JNJ")
			valid = false;
		if(result.get(2).get(1) != "10")
			valid = false;
		
		assertTrue(valid);
	}

	@Test
	public void testGetLineForPortfolioWithDateRange() {
		Portfolio.addStock(1, "NTNX", 10, "09-05-2020"); // buy 10 "NTNX" stocks
		Portfolio.addStock(1, "JNJ", 10, "09-30-2020"); // buy 10 "JNJ" stocks
		
		ArrayList<ArrayList<String>> result = Portfolio.getLineForPortfolioWithDateRange(1, "09-01-2020", "09-06-2020");
		
		boolean valid = true;
		
		//NOTE: This test case still has to be fixed
		//Check each entry
		if(result.get(0).get(0) != "Date")
			valid = false;
		if(result.get(0).get(1) != "Value")
			valid = false;
		if(result.get(1).get(0) != "NTNX")
			valid = false;	
		if(result.get(1).get(1) != "10")
			valid = false;
		if(result.get(2).get(0) != "JNJ")
			valid = false;
		if(result.get(2).get(1) != "10")
			valid = false;
		
		assertTrue(valid);
	}

	@Test
	public void testGetPortfolioValueOnADate() {
		Portfolio.addStock(1, "NTNX", 10, "09-05-2020"); // buy 10 "NTNX" stocks
		Portfolio.addStock(1, "JNJ", 10, "09-30-2020"); // buy 10 "JNJ" stocks
		
		String result = Portfolio.getPortfolioValueOnADate(1, "09-10-2020");
		double value = Double.parseDouble(result);
		
		boolean valid = (value > 50);
		assertTrue(valid);
	}

}
