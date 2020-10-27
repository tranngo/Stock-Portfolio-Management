package csci310;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

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

	@Test
	public void testAddStock() {
		int result = Portfolio.addStock(1, ntnx, 10, "09-05-2020");
		assertEquals(result, 1);
		
		result = Portfolio.addStock(1, ntnx, -1, "09-05-2020");
		assertEquals(result, 0);
		
		// reset db
		removeStockTransaction(1, "bought", ntnx, 10, "09-05-2020");
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
		Portfolio.addStock(1, ntnx, 10, "09-05-2020"); // buy 10 ntnx stocks
		int result = Portfolio.sellStock(1, ntnx, 5, "09-05-2020"); //sell 5
		assertEquals(result, 1);
		
		result = Portfolio.sellStock(1, ntnx, 100, "09-05-2020"); //sell 100
		assertEquals(result, 0);
		
		result = Portfolio.sellStock(1, ntnx, -1, "09-05-2020"); //sell -1
		assertEquals(result, 0);
		
		// reset db
		removeStockTransaction(1, "sold", ntnx, 5, "09-05-2020");
	}

	// Messed up this test!
	@Test
	public void testRetrieveCurrentPortfolio() {
		Portfolio.addStock(1, ntnx, 10, "09-05-2020"); // buy 10 ntnx stocks
		Portfolio.addStock(1, jnj, 10, "09-05-2020"); // buy 10 jnj stocks
		ArrayList<ArrayList<String>> result = Portfolio.retrieveCurrentPortfolio(1);
		assertEquals(3, result.size());
		
		//Print statements
		System.out.println("testRetrieveCurrentPortfolio's result for NTNX and JNJ: ");
		for(int i = 0; i < result.size(); i++) {
			for(int j = 0; j < result.get(i).size(); j++) {
				System.out.print(result.get(i).get(j) + ", ");
			}
			System.out.println("");
		}
		
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
		
		Portfolio.sellStock(1, jnj, 10, "09-05-2020");
		result = Portfolio.retrieveCurrentPortfolio(1);
		boolean temp = (result.size() >= 1);
		assertTrue(temp);
				
		// reset db
		removeStockTransaction(1, "bought", ntnx, 10, "09-05-2020");
		removeStockTransaction(1, "bought", jnj, 10, "09-05-2020");
		removeStockTransaction(1, "sold", jnj, 10, "09-05-2020");
	}
	
	// Messed up this test!
	@Test
	public void testRetrievePortfolioOnADate() {
		Portfolio.addStock(1, ntnx, 10, "09-05-2020"); // buy 10 ntnx stocks
		Portfolio.addStock(1, ntnx, 10, "09-06-2020"); // buy 10 ntnx stocks
		Portfolio.addStock(1, jnj, 10, "09-30-2020"); // buy 10 jnj stocks
		ArrayList<ArrayList<String>> result = Portfolio.retrievePortfolioOnADate(1, "09-06-2020");
		boolean temp = (result.size() >= 1);
		assertTrue(temp);
		
		//Print statements
		System.out.println("testRetrievePortfolioOnADate's result for NTNX and JNJ: ");
		for(int i = 0; i < result.size(); i++) {
			for(int j = 0; j < result.get(i).size(); j++) {
				System.out.print(result.get(i).get(j) + ", ");
			}
			System.out.println("");
		}
		
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
		removeStockTransaction(1, "bought", ntnx, 10, "09-05-2020");
		removeStockTransaction(1, "bought", ntnx, 10, "09-06-2020");
		removeStockTransaction(1, "bought", jnj, 10, "09-30-2020");
	}

	@Test
	public void testGetCurrentPortfolioValue() {
		Portfolio.addStock(1, ntnx, 10, "09-05-2020"); // buy 10 ntnx stocks
		Portfolio.addStock(1, jnj, 10, "09-05-2020"); // buy 10 jnj stocks
		
		String result = Portfolio.getCurrentPortfolioValue(1);
		double value = Double.parseDouble(result);
		
		boolean valid = (value > 500); 
		assertTrue(valid);
		
		// reset db
		removeStockTransaction(1, "bought", ntnx, 10, "09-05-2020");
		removeStockTransaction(1, "bought", jnj, 10, "09-05-2020");
	}

	@Test
	public void testGetFullLineForPortfolio() {
		
		Portfolio.addStock(1, ntnx, 10, "09-05-2020"); // buy 10 ntnx stocks
		Portfolio.addStock(1, jnj, 10, "09-07-2020"); // buy 10 jnj stocks
		Portfolio.addStock(1, jnj, 10, "09-08-2020"); // buy 10 jnj stocks
		
		ArrayList<ArrayList<String>> result = Portfolio.getFullLineForPortfolio(1);
		if(result == null) {
			System.out.println("PortfolioTest.java, testGetFullLineForPortfolio, null");
			return;
		}
		
		boolean temp = (result.size() >= 1);
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
		removeStockTransaction(1, "bought", ntnx, 10, "09-05-2020");
		removeStockTransaction(1, "bought", jnj, 10, "09-07-2020");
		removeStockTransaction(1, "bought", jnj, 10, "09-08-2020");
	}

	@Test
	public void testGetLineForPortfolioWithDateRange() {
		Portfolio.addStock(1, ntnx, 10, "09-05-2020"); // buy 10 ntnx stocks
		Portfolio.addStock(1, jnj, 10, "09-30-2020"); // buy 10 jnj stocks
		
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
		removeStockTransaction(1, "bought", ntnx, 10, "09-05-2020");
		removeStockTransaction(1, "bought", jnj, 10, "09-30-2020");
	}

	@Test
	public void testGetPortfolioValueOnADate() {
		Portfolio.addStock(1, ntnx, 10, "09-05-2020"); // buy 10 ntnx stocks
		Portfolio.addStock(1, jnj, 10, "09-30-2020"); // buy 10 jnj stocks
		
		String result = Portfolio.getPortfolioValueOnADate(1, "09-10-2020");
		if(result == "") {
			System.out.println("PortfolioTest.java, testGetPortfolioValueOnADate, empty string");
			return;
		}
		
		double value = Double.parseDouble(result);
		
		boolean valid = (value > 50);
		assertTrue(valid);
		
		// reset db
		removeStockTransaction(1, "bought", ntnx, 10, "09-05-2020");
		removeStockTransaction(1, "bought", jnj, 10, "09-30-2020");
	}
	
	private void removeStockTransaction(
			int userId, String transaction, String stock, int quantity, String date) {
		JDBC db = new JDBC();
		Connection con = db.connectDB("com.mysql.cj.jdbc.Driver", "jdbc:mysql://remotemysql.com:3306/DT6BLiMGub","DT6BLiMGub","W1B4BiSiHP");
		
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

}