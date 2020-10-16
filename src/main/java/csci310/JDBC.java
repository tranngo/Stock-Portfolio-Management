package csci310;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class JDBC {
	// open connection to mysql db
		public static Connection connectDB(){
	        try {
	        	Class.forName("com.mysql.cj.jdbc.Driver");
				/*Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stocks?" + 
						"useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=PST",
						"root",
						readDBCredentials()); */
	        	Connection con = DriverManager.getConnection("jdbc:mysql://remotemysql.com:3306/DT6BLiMGub","DT6BLiMGub","W1B4BiSiHP");
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
		        }
		        myReader.close();
	        } catch (FileNotFoundException e) {
	        	// error opening file
	        	System.out.println("Error opening db-credentials.txt; Returning default password.");
	        }
	        return dbPassword;
		}
}
