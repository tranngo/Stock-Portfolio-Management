package csci310;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *	In Register.java we implement all the helper methods we need to
 *	enter a new user into our database. Including hashing passwords,
 *	validating the info entered into the form, and lastly putting the
 *	user and the SHA-256 hash of their password into the database.
 */
public class Register {
	
	//Check if user already exists in database
	public static boolean checkUserExists(String username)
	{
		// connect to mysql
		Connection con = JDBC.connectDB();
		
		if(con != null) {
			try {
				// query users table for username parameter
				PreparedStatement ps = con.prepareStatement("SELECT * FROM users WHERE BINARY username = ?");
				ps.setString(1, username);
				ResultSet rs = ps.executeQuery();
	
				return rs.next();
	        } catch (SQLException e) {
	        	System.out.println("Error querying user data from DB during registration.");
	        	e.printStackTrace();
	        }
		}
		
		// error querying users table; for security, assume username is taken
		return true;
	}
	
	//Insert user registration info into the database
	public static boolean insertUser(String username, String hashed_password)
	{
		// connect to mysql
		Connection con = JDBC.connectDB();
		
		if(con != null) {
			try {
			    // insert entry into users table
			    PreparedStatement ps = con.prepareStatement("INSERT into users (username, password) VALUES (?, ?)");
			    ps.setString (1, username);
			    ps.setString (2, hashed_password);
			    ps.execute();
	
				return true; // successful insertion
			} catch (SQLException e) {
				System.out.println("Error inserting user data to DB during registration.");
				e.printStackTrace();
			}
		}
		
		return false; // error connecting to db or failed insertion
	}
	
}
