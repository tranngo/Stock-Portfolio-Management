package csci310;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

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
		Connection con = connectDB();
		
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
		Connection con = connectDB();
		
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
	
	// open connection to mysql db
	public static Connection connectDB(){
        try {
        	Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stocks?" + 
					"useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=PST",
					"root",
					readDBCredentials());
            return con;
        } catch (ClassNotFoundException e) {
			e.printStackTrace();
        } catch (SQLException e) {
        	System.out.println("Error connecting to MySQL Workbench; Please check account credentials.");
        	e.printStackTrace();
        }
        return null;
    }
	
	// private method to retrieve private db password from "db-credentials.txt" file
	private static String readDBCredentials() {
		String dbPassword = ""; // default pass
        try {
        	// open file
        	File myObj = new File("db-credentials.txt");
	        Scanner myReader = new Scanner(myObj);
	        
	        // check if password exists in file
	        while(myReader.hasNextLine()) {
	          String line = myReader.nextLine();
	          if(!line.isEmpty()) {
	        	  dbPassword = line.trim();
	        	  break;
	          }
	          else {
	        	  System.out.println("Debug: Else in Register.java readDBCredentials");
	          }
	        }
	        myReader.close();
        } catch (FileNotFoundException e) {
        	// error opening file
        	System.out.println("Error opening db-credentials.txt; Returning default password.");
        }
        return dbPassword;
	}
}