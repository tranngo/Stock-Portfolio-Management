package csci310;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class JDBC {
	// open connection to mysql db
		public Connection connectDB(String class_name, String connection1, String connection2, String connection3){
	        try {
	        	Class.forName(class_name);
	        	Connection con = DriverManager.getConnection(connection1,connection2,connection3);
	            return con;
	        } catch (ClassNotFoundException e) {
				e.printStackTrace();
	        } catch (SQLException e) {
	        	// System.out.println("Error connecting to MySQL Workbench; Please check account credentials.");
	        	e.printStackTrace();
	        }
	        return null;
	    }
}
