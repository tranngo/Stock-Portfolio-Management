package csci310;

import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import com.google.common.hash.Hashing;

/**
 *	In Register.java we implement all the helper methods we need to
 *	enter a new user into our database. Including hashing passwords,
 *	validating the info entered into the form, and lastly putting the
 *	user and the SHA-256 hash of their password into the database.
 */
public class Register {

	//Check that the username and password are valid
	public static boolean validateUserInfo(String username, String password, String confirmPassword)
	{
		ArrayList<String> invalidChars = new ArrayList<String>(
				Arrays.asList("'", "\"", ";"));
		
		// username and password should not be empty strings
		if(username.isEmpty() || password.isEmpty()) {
			return false;
		}
		
		// username and password should not contain invalid characters
		for(String invalidChar: invalidChars) {
			if(username.contains(invalidChar) || password.contains(invalidChar)) {
				return false;
			}
		}
		
		//password does not match confirm password
		if(password.equals(confirmPassword) == false) {
			return false;
		}
		
		// else, password is valid
		return true;
	}
	
	//Hash a password with SHA 256
	public static String hashPasswordWithSHA256(String password)
	{
		// hash password and return as hex string
		String sha256hex = Hashing.sha256()
				  .hashString(password, StandardCharsets.UTF_8)
				  .toString();
		return sha256hex;
	}
	
	//Check if user already exists in database
	public static boolean checkUserExists(String username)
	{
		// connect to mysql
		JDBC db = new JDBC();
		Connection con = db.connectDB("com.mysql.cj.jdbc.Driver", "jdbc:mysql://remotemysql.com:3306/DT6BLiMGub","DT6BLiMGub","W1B4BiSiHP");
		
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
	        } finally {
			    if (con != null) {
			        try {
			            con.close();
			        } catch (SQLException e) { /* ignored */}
			    }
			}
		}
		
		// error querying users table; for security, assume username is taken
		return true;
	}
	
	//Insert user registration info into the database
	public static boolean insertUser(String username, String hashed_password)
	{
		// connect to mysql
		JDBC db = new JDBC();
		Connection con = db.connectDB("com.mysql.cj.jdbc.Driver", "jdbc:mysql://remotemysql.com:3306/DT6BLiMGub","DT6BLiMGub","W1B4BiSiHP");
		
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
			finally {
			    if (con != null) {
			        try {
			            con.close();
			        } catch (SQLException e) { /* ignored */}
			    }
			}
		}
		
		return false; // error connecting to db or failed insertion
	}
}