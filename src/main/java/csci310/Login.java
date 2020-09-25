package csci310;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Login {	
	public static boolean checkForLoginCredentials(String username, String password) {
		// connect to mysql
		Connection con = JDBC.connectDB();
		
		if(con != null) {
			try {
				// query users table for username parameter
				PreparedStatement ps = con.prepareStatement("SELECT * FROM users WHERE username == " + username + " AND password == " + password);
				ResultSet rs = ps.executeQuery();
	
				return rs.next();
	        } catch (SQLException e) {
	        	System.out.println("Error querying user data from DB during registration.");
	        	e.printStackTrace();
	        }
		}
		
		// error querying users table; for security, assume wrong credentials
		return false;
	}
	
	

}
