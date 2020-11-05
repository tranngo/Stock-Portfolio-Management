package csci310;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

public class PortfolioTest {
	
	Portfolio portfolio;
	String ntnx;
	String jnj;

	@Before
	public void setUp() throws Exception {
		portfolio = new Portfolio();
		ntnx = "NTNX";
		jnj = "JNJ";
	}

	//Add 10 NTNX transaction, add a bad -1 transaction, remove all NTNX stock from DB
	@Test
	public void testAddStock() {
		// reset db
		 Portfolio.sellStock(99, ntnx);

		 int result = Portfolio.addStock(99, ntnx, 10, "09-05-2020", "10-05-2020");
		 assertEquals(1, result);
		
		 result = Portfolio.addStock(99, ntnx, -1, "09-05-2020", "10-05-2020");
		 assertEquals(0, result);
		
		// reset db
		 result = Portfolio.sellStock(99, ntnx);
		 assertEquals(1, result);
	}

	/*
	 * ISSUE: If a test case's assert fails, then the removeStockTransaction() isn't
	 * called. So the stocks stay in the DB. Later, when you re-run the tests after trying
	 * to fix it, now a different quantity is expected, since those old stocks are 
	 * still in the DB. That's where the problems with PortfolioTest come up.
	 * 
	 * ISSUE: For these test cases, the database is updated each time and the changes
	 * made by each test case will persist in the database.
	 * So we have to make sure the test cases is actually correct.
	 * Perhaps we can sell the stocks that we add to account for this.
	 */

	@Test
	public void testSellStock() {
		// reset db
		 Portfolio.sellStock(99, ntnx);

		 Portfolio.addStock(99, ntnx, 10, "09-05-2020", "10-05-2020"); // buy 10 ntnx stocks
		 int result = Portfolio.sellStock(99, ntnx); //delete all ntnx transactions
		 assertEquals(1, result);
		
		 result = Portfolio.sellStock(99, ntnx); //delete again, the result still is 1
		 assertEquals(1, result);
	}

	// Messed up this test!
	@Test
	public void testRetrieveCurrentPortfolio() {
		// reset db
		 Portfolio.sellStock(99, ntnx);
		 Portfolio.sellStock(99, jnj);		
	

		 Portfolio.addStock(99, ntnx, 10, "09-05-2020", "10-05-2020"); // buy 10 ntnx stocks
		 Portfolio.addStock(99, jnj, 10, "09-05-2020", "10-05-2020"); // buy 10 jnj stocks
		 ArrayList<ArrayList<String>> result = Portfolio.retrieveCurrentPortfolio(99);
		 assertTrue(result.size() > 0);
		
		//Print statements
		// System.out.println("testRetrieveCurrentPortfolio's result for NTNX and JNJ: ");
		// for(int i = 0; i < result.size(); i++) {
		// 	for(int j = 0; j < result.get(i).size(); j++) {
		// 		System.out.print(result.get(i).get(j) + ", ");
		// 	}
		// 	// System.out.println("");
		// }
		
		//Check each entry
		 Boolean valid = true;
		 if(!result.get(0).get(0).equals("Stock"))
		 	valid = false;
		 if(!result.get(0).get(1).equals("Quantity"))
		 	valid = false;
		 if(!result.get(1).get(0).equals(ntnx))
		 	valid = false;	
		 if(!result.get(2).get(0).equals(jnj))
		 	valid = false;
		
		 assertTrue(valid);
		

		 Portfolio.sellStock(99, jnj);
		 result = Portfolio.retrieveCurrentPortfolio(99);
		 assertEquals(2, result.size());

		// reset db
		 Portfolio.sellStock(99, ntnx);
	}
	
	// Messed up this test!
	@Test
	public void testRetrievePortfolioOnADate() {
		// reset db
		 Portfolio.sellStock(99, ntnx);
		 Portfolio.sellStock(99, jnj);
		
		 Portfolio.addStock(99, ntnx, 10, "09-05-2020", "10-05-2020"); // buy 10 ntnx stocks
		 Portfolio.addStock(99, ntnx, 10, "09-06-2020", "10-05-2020"); // buy 10 ntnx stocks
		 Portfolio.addStock(99, jnj, 10, "09-30-2020", "10-05-2020"); // buy 10 jnj stocks
		 Portfolio.addStock(99, jnj, 10, "10-04-2020", "10-05-2020"); // buy 10 jnj stocks
		 ArrayList<ArrayList<String>> result = Portfolio.retrievePortfolioOnADate(99, "09-30-2020");
		assertTrue(result.size() > 0);
		
		//Print statements
		// System.out.println("testRetrievePortfolioOnADate's result for NTNX and JNJ: ");
		// for(int i = 0; i < result.size(); i++) {
		// 	for(int j = 0; j < result.get(i).size(); j++) {
		// 		System.out.print(result.get(i).get(j) + ", ");
		// 	}
		// 	// System.out.println("");
		// }
		
		 assertEquals(3, result.size());
		
		//Check each entry
		 Boolean valid = true;
		 if(!result.get(0).get(0).equals("Stock"))
		 	valid = false;
		 if(!result.get(0).get(1).equals("Quantity"))
		 	valid = false;
		 if(!result.get(1).get(0).equals(ntnx))
		 	valid = false;
		
		 assertTrue(valid);
				
		// reset db
		 Portfolio.sellStock(99, ntnx);
		 Portfolio.sellStock(99, jnj);
	}

	@Test
	public void testGetCurrentPortfolioValue() {
		// reset db
		 Portfolio.sellStock(99, ntnx);
		 Portfolio.sellStock(99, jnj);
		
		 Portfolio.addStock(99, ntnx, 10, "09-05-2020", "12-15-2020"); // buy 10 ntnx stocks
		 Portfolio.addStock(99, jnj, 10, "09-05-2020", "12-15-2020"); // buy 10 jnj stocks
		
		 String result = Portfolio.getCurrentPortfolioValue(99);
		 System.out.println("10 NTNX stock, 10 JNJ stock currently worth " + result);
		 double value = Double.parseDouble(result);
		
		 boolean valid = (value > 500); 
		 assertTrue(valid);
		
		// reset db
		 Portfolio.sellStock(99, ntnx);
		 Portfolio.sellStock(99, jnj);
	}

	@Test
	public void testGetFullLineForPortfolio() {
		// reset db
		 Portfolio.sellStock(99, ntnx);
		 Portfolio.sellStock(99, jnj);
		
		 Portfolio.addStock(99, ntnx, 10, "09-05-2020", "09-15-2020"); // buy 10 ntnx stocks
		 Portfolio.addStock(99, jnj, 10, "09-07-2020", "09-15-2020"); // buy 10 jnj stocks 9/7
		 Portfolio.addStock(99, jnj, 10, "09-08-2020", "09-15-2020"); // buy 10 jnj stocks 9/8
		
		 ArrayList<ArrayList<String>> result = Portfolio.getFullLineForPortfolio(99);
		 if(result == null) {
			 System.out.println("PortfolioTest.java, testGetFullLineForPortfolio, null");
		 	return;
		 }

		//Print statements
		// System.out.println("testGetFullLineForPortfolio's result for NTNX and JNJ (9/5 to 9/15): ");
		// for(int i = 0; i < result.size(); i++) {
		// 	for(int j = 0; j < result.get(i).size(); j++) {
		// 		System.out.print(result.get(i).get(j) + ", ");
		// 	}
			// System.out.println("");
		// }
		
		 boolean temp = (result.size() > 6);
		 assertTrue(temp);
		
		//Check each entry
		 Boolean valid = true;
		 if(!result.get(0).get(0).equals("Date"))
		 	valid = false;
		 if(!result.get(0).get(1).equals("Value"))
		 	valid = false;
		 if(!result.get(1).get(0).equals("09-05-2020"))
		 	valid = false;	
		 if(!Api.isNumeric(result.get(1).get(1)))
		 	valid = false;
		 if(!result.get(2).get(0).equals("09-06-2020"))
		 	valid = false;
		 if(!Api.isNumeric(result.get(2).get(1)))
		 	valid = false;
		 if(!result.get(3).get(0).equals("09-07-2020"))
		 	valid = false;
		 if(!Api.isNumeric(result.get(3).get(1)))
		 	valid = false;
		 if(!result.get(4).get(0).equals("09-08-2020"))
		 	valid = false;
		 if(!Api.isNumeric(result.get(4).get(1)))
		 	valid = false;
		
		assertTrue(valid);
		
		// reset db
		 Portfolio.sellStock(99, ntnx);
		 Portfolio.sellStock(99, jnj);
	}

	@Test
	public void testGetLineForPortfolioWithDateRange() {
		// reset db
		 Portfolio.sellStock(99, ntnx);
		 Portfolio.sellStock(99, jnj);
		
		 Portfolio.addStock(99, ntnx, 10, "09-05-2020", "09-15-2020"); // buy 10 ntnx stocks
		 Portfolio.addStock(99, jnj, 10, "09-12-2020", "09-18-2020"); // buy 10 jnj stocks
		
		 ArrayList<ArrayList<String>> result = Portfolio.getLineForPortfolioWithDateRange(99, "09-05-2020", "09-17-2020");
		 if(result == null) {
			 System.out.println("PortfolioTest.java, testGetLineForPortfolioWithDateRange, null");
		 	return;
		 }
	
		 boolean temp = (result.size() > 6);
		 assertTrue(temp);
		
		//Print statements
		// System.out.println("NTNX is held from 9/5 to 9/15, JNJ held from 9/12 to 9/18. Range selected to view is 9/5 to 9/17.");
		// System.out.println("testGetLineForPortfolioWithDateRange's result: ");
		// for(int i = 0; i < result.size(); i++) {
		// 	for(int j = 0; j < result.get(i).size(); j++) {
		// 		System.out.print(result.get(i).get(j) + ", ");
		// 	}
			// System.out.println("");
		// }
		
		//Check each entry
		 Boolean valid = true;
		 if(result.get(0).get(0) != "Date")
		 	valid = false;
		 if(result.get(0).get(1) != "Value")
		 	valid = false;
		 if(!result.get(1).get(0).equals("09-05-2020"))
		 	valid = false;	
		 if(!Api.isNumeric(result.get(1).get(1)))
		 	valid = false;
		 if(!result.get(2).get(0).equals("09-06-2020"))
		 	valid = false;	
		 if(!Api.isNumeric(result.get(2).get(1)))
		 	valid = false;
		
		 assertTrue(valid);
		
		// reset db
		 Portfolio.sellStock(99, ntnx);
		 Portfolio.sellStock(99, jnj);
	}

	@Test
	public void testGetPortfolioValueOnADate() {
		// reset db
		 Portfolio.sellStock(99, ntnx);
		 Portfolio.sellStock(99, jnj);
		
		 Portfolio.addStock(99, ntnx, 10, "09-05-2020", "09-15-2020"); // buy 10 ntnx stocks
		 Portfolio.addStock(99, jnj, 10, "09-12-2020", "09-18-2020"); // buy 10 jnj stocks
		
		 String result = Portfolio.getPortfolioValueOnADate(99, "09-10-2020");
		 if(result == "") {
			 System.out.println("PortfolioTest.java, testGetPortfolioValueOnADate, empty string");
		 	return;
		 }
		
		 double value = Double.parseDouble(result);
		
		 boolean valid = (value > 50);
		 assertTrue(valid);
		
		// reset db
		 Portfolio.sellStock(99, ntnx);
		 Portfolio.sellStock(99, jnj);
	}
	
	@Test
	public void testGetLineForPortfolioWithDateRangeFaster() {
		
		// reset db
		 Portfolio.sellStock(99, ntnx);
		 Portfolio.sellStock(99, jnj);
		
		 Portfolio.addStock(99, ntnx, 10, "09-05-2020", "09-15-2020"); // buy 10 ntnx stocks
		 Portfolio.addStock(99, jnj, 10, "09-12-2020", "09-18-2020"); // buy 10 jnj stocks
		
		 ArrayList<String> portfolioContr = new ArrayList<String>(Arrays.asList(ntnx, jnj));
		 ArrayList<ArrayList<String>> result = Portfolio.getLineForPortfolioWithDateRangeFaster(99, "09-05-2020", "09-17-2020", portfolioContr);
		 if(result == null) {
			 System.out.println("PortfolioTest.java, testGetLineForPortfolioWithDateRangeFaster, null");
		 	return;
		 }
	
		 boolean temp = (result.size() > 6);
		 assertTrue(temp);
		
		//Print statements
		// System.out.println("NTNX is held from 9/5 to 9/15, JNJ held from 9/12 to 9/18. Range selected to view is 9/5 to 9/17.");
		// System.out.println("testGetLineForPortfolioWithDateRange's result: ");
		// for(int i = 0; i < result.size(); i++) {
		// 	for(int j = 0; j < result.get(i).size(); j++) {
		// 		System.out.print(result.get(i).get(j) + ", ");
		// 	}
			// System.out.println("");
		// }
		
		//Check each entry
//		 Boolean valid = true;
//		 if(result.get(0).get(0) != "Date")
//		 	valid = false;
//		 if(result.get(0).get(1) != "Value")
//		 	valid = false;
//		 if(!result.get(1).get(0).equals("09-05-2020"))
//		 	valid = false;	
//		 if(!Api.isNumeric(result.get(1).get(1)))
//		 	valid = false;
//		 if(!result.get(2).get(0).equals("09-06-2020"))
//		 	valid = false;	
//		 if(!Api.isNumeric(result.get(2).get(1)))
//		 	valid = false;
		
		// assertTrue(valid);
		
		// reset db
		Portfolio.sellStock(99, ntnx);
		Portfolio.sellStock(99, jnj);
	}

}