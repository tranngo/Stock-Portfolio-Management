package csci310;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import com.google.common.hash.Hashing;

public class Login {
	
	// Check the validity of username and password
	public static boolean validateUserInfo(String username, String password) {
		ArrayList<String> invalidChars = new ArrayList<String>(Arrays.asList("'", "\"", ";"));
		
		// username and passowrd should not be empty strings
		if (username.isEmpty() || password.isEmpty())
			return false;
		
		// username and password should not contain invalid characters
		for (String invalidChar : invalidChars)
			if (username.contains(invalidChar) || password.contains(invalidChar))
				return false;
		
		// valid password
		return true;

	}
	
	// Hash a password with SHA 256
	public static String hashPasswordWithSHA256(String password) throws NoSuchAlgorithmException {
		return Hashing.sha256().hashString(password, StandardCharsets.UTF_8).toString();
	}
	
	public static boolean checkForLoginCredentials(String username, String password) {
		// connect to mysql
		Connection con = connectDB();
		
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
		String dbPassword = "password"; // default pass
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
	        }
	        myReader.close();
        } catch (FileNotFoundException e) {
        	// error opening file
        	System.out.println("Error opening db-credentials.txt; Returning default password.");
        }
        return dbPassword;
	}

}
