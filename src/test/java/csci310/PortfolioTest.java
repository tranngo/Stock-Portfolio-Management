package csci310;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class PortfolioTest {
	
	Portfolio portfolio;

	@Before
	public void setUp() throws Exception {
		portfolio = new Portfolio();
	}
	
	@Test
	public void testAddTransaction() {
		int result = Portfolio.addTransaction(1, "NTNX", 10, "09-05-2020", "10-05-202");
		assertEquals(result, 1);
		
		// reset db
		assertTrue(removeStockTransaction(1, "bought", "NTNX", 10, "09-05-2020"));
		assertTrue(removeStockTransaction(1, "sold", "NTNX", 10, "10-05-2020"));
	}

	@Test
	public void testAddStock() {
		int result = Portfolio.addStock(1, "NTNX", 10, "09-05-2020");
		assertEquals(result, 1);
		
		result = Portfolio.addStock(1, "NTNX", -1, "09-05-2020");
		assertEquals(result, 0);
		
		// reset db
		removeStockTransaction(1, "bought", "NTNX", 10, "09-05-2020");
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
		Portfolio.addStock(1, "NTNX", 10, "09-05-2020"); // buy 10 "NTNX" stocks
		int result = Portfolio.sellStock(1, "NTNX", 5, "09-05-2020"); //sell 5
		assertEquals(result, 1);
		
		result = Portfolio.sellStock(1, "NTNX", 100, "09-05-2020"); //sell 100
		assertEquals(result, 0);
		
		result = Portfolio.sellStock(1, "NTNX", -1, "09-05-2020"); //sell -1
		assertEquals(result, 0);
		
		// reset db
		removeStockTransaction(1, "sold", "NTNX", 5, "09-05-2020");
	}

	@Test
	public void testRetrieveCurrentPortfolio() {
		Portfolio.addStock(1, "NTNX", 10, "09-05-2020"); // buy 10 "NTNX" stocks
		Portfolio.addStock(1, "JNJ", 10, "09-05-2020"); // buy 10 "JNJ" stocks
		ArrayList<ArrayList<String>> result = Portfolio.retrieveCurrentPortfolio(1);
		assertEquals(3, result.size());
		
		//Check each entry
		Boolean valid = true;
		if(!result.get(0).get(0).equals("Stock"))
			valid = false;
		if(!result.get(0).get(1).equals("Quantity"))
			valid = false;
		if(!result.get(1).get(0).equals("NTNX"))
			valid = false;	
		if(!result.get(1).get(1).equals("10"))
			valid = false;
		if(!result.get(2).get(0).equals("JNJ"))
			valid = false;
		if(!result.get(2).get(1).equals("10"))
			valid = false; 
		
		assertTrue(valid);
		
		Portfolio.sellStock(1, "JNJ", 10, "09-05-2020");
		result = Portfolio.retrieveCurrentPortfolio(1);
		assertEquals(2, result.size());
				
		// reset db
		removeStockTransaction(1, "bought", "NTNX", 10, "09-05-2020");
		removeStockTransaction(1, "bought", "JNJ", 10, "09-05-2020");
		removeStockTransaction(1, "sold", "JNJ", 10, "09-05-2020");
	}
	
	@Test
	public void testRetrievePortfolioOnADate() {
		Portfolio.addStock(1, "NTNX", 10, "09-05-2020"); // buy 10 "NTNX" stocks
		Portfolio.addStock(1, "NTNX", 10, "09-06-2020"); // buy 10 "NTNX" stocks
		Portfolio.addStock(1, "JNJ", 10, "09-30-2020"); // buy 10 "JNJ" stocks
		ArrayList<ArrayList<String>> result = Portfolio.retrievePortfolioOnADate(1, "09-06-2020");
		assertEquals(2, result.size());
		
		//Check each entry
		Boolean valid = true;
		if(!result.get(0).get(0).equals("Stock"))
			valid = false;
		if(!result.get(0).get(1).equals("Quantity"))
			valid = false;
		if(!result.get(1).get(0).equals("NTNX"))
			valid = false;	
		if(!result.get(1).get(1).equals("20"))
			valid = false;
		
		assertTrue(valid);
				
		// reset db
		removeStockTransaction(1, "bought", "NTNX", 10, "09-05-2020");
		removeStockTransaction(1, "bought", "NTNX", 10, "09-06-2020");
		removeStockTransaction(1, "bought", "JNJ", 10, "09-30-2020");
	}

	@Test
	public void testGetCurrentPortfolioValue() {
		Portfolio.addStock(1, "NTNX", 10, "09-05-2020"); // buy 10 "NTNX" stocks
		Portfolio.addStock(1, "JNJ", 10, "09-05-2020"); // buy 10 "JNJ" stocks
		
		String result = Portfolio.getCurrentPortfolioValue(1);
		double value = Double.parseDouble(result);
		
		boolean valid = (value > 500); 
		assertTrue(valid);
		
		// reset db
		removeStockTransaction(1, "bought", "NTNX", 10, "09-05-2020");
		removeStockTransaction(1, "bought", "JNJ", 10, "09-05-2020");
	}

	@Test
	public void testGetFullLineForPortfolio() {
		
		Portfolio.addStock(1, "NTNX", 10, "09-05-2020"); // buy 10 "NTNX" stocks
		Portfolio.addStock(1, "JNJ", 10, "09-07-2020"); // buy 10 "JNJ" stocks
		Portfolio.addStock(1, "JNJ", 10, "09-08-2020"); // buy 10 "JNJ" stocks
		
		ArrayList<ArrayList<String>> result = Portfolio.getFullLineForPortfolio(1);
		if(result == null) {
			System.out.println("PortfolioTest.java, testGetFullLineForPortfolio, null");
			return;
		}
		
		assertEquals(5, result.size());
		
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
		removeStockTransaction(1, "bought", "NTNX", 10, "09-05-2020");
		removeStockTransaction(1, "bought", "JNJ", 10, "09-07-2020");
		removeStockTransaction(1, "bought", "JNJ", 10, "09-08-2020");
	}

	@Test
	public void testGetLineForPortfolioWithDateRange() {
		Portfolio.addStock(1, "NTNX", 10, "09-05-2020"); // buy 10 "NTNX" stocks
		Portfolio.addStock(1, "JNJ", 10, "09-30-2020"); // buy 10 "JNJ" stocks
		
		ArrayList<ArrayList<String>> result = Portfolio.getLineForPortfolioWithDateRange(1, "09-01-2020", "09-06-2020");
		if(result == null) {
			System.out.println("PortfolioTest.java, testGetLineForPortfolioWithDateRange, null");
			return;
		}
	
		assertEquals(3, result.size());
		
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
		removeStockTransaction(1, "bought", "NTNX", 10, "09-05-2020");
		removeStockTransaction(1, "bought", "JNJ", 10, "09-30-2020");
	}

	@Test
	public void testGetPortfolioValueOnADate() {
		Portfolio.addStock(1, "NTNX", 10, "09-05-2020"); // buy 10 "NTNX" stocks
		Portfolio.addStock(1, "JNJ", 10, "09-30-2020"); // buy 10 "JNJ" stocks
		
		String result = Portfolio.getPortfolioValueOnADate(1, "09-10-2020");
		if(result == "") {
			System.out.println("PortfolioTest.java, testGetPortfolioValueOnADate, empty string");
			return;
		}
		
		double value = Double.parseDouble(result);
		
		boolean valid = (value > 50);
		assertTrue(valid);
		
		// reset db
		removeStockTransaction(1, "bought", "NTNX", 10, "09-05-2020");
		removeStockTransaction(1, "bought", "JNJ", 10, "09-30-2020");
	}
	
	@Test
	public void testRemoveStockFromPortfolio() {
		Portfolio.addStock(1, "NTNX", 10, "09-05-2020"); // buy 10 "NTNX" stocks
		
		ArrayList<ArrayList<String>> result = Portfolio.retrieveCurrentPortfolio(1);
		assertEquals(2, result.size());
		
		// remove NTNX
		Portfolio.removeStockFromPortfolio(1, "NTNX");
		Connection con = JDBC.connectDB();
		if(con != null) {
			try {
			    // insert entry into users table
			    PreparedStatement ps = con.prepareStatement("SELECT * from stocks WHERE user_id = ? " +
			    		"AND name = ? ");
			    ps.setInt(1, 1);
			    ps.setString(2, "NTNX");
			    ResultSet rs = ps.executeQuery();
				assertFalse(rs.next());
			} catch (SQLException e) {
				System.out.println("Error inserting user data to DB when adding to stocks.");
				e.printStackTrace();
			} finally {
	            try {
	                if(con != null) {
	                    con.close();
	                }
	            } catch (SQLException ex) {
	                System.out.println(ex.getMessage());
	            }
	        }
		}
	}
	
	private Boolean removeStockTransaction(
			int userId, String transaction, String stock, int quantity, String date) {
		Connection con = JDBC.connectDB();
		
		boolean success = false;
		if(con != null) {
			try {
			    // insert entry into users table
			    PreparedStatement ps = con.prepareStatement("DELETE from stocks WHERE user_id = ? " +
			    		"AND transaction = ? " +
			    		"AND name = ? " +
			    		"AND quantity = ? " + 
			    		"AND date = ? ");
			    ps.setInt(1, userId);
			    ps.setString(2, transaction);
			    ps.setString(3, stock);
			    ps.setInt(4, quantity);
			    ps.setString(5, date);
			    ps.execute();
			    success = true;
			} catch (SQLException e) {
				success = false;
				System.out.println("Error inserting user data to DB when adding to stocks.");
				e.printStackTrace();
			} finally {
	            try {
	                if(con != null) {
	                    con.close();
	                }
	            } catch (SQLException ex) {
	                System.out.println(ex.getMessage());
	            }
	        }
		}
		return success;
	}

}
