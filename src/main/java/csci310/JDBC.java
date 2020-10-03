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
				Connection con = DriverManager.getConnection("jdbc:mysql://remotemysql.com:3036/DT6BLiMGuB",
						"DT6BLiMGuB", "W1B4BiSiHP");
	            return con;
	        } catch (ClassNotFoundException e) {
				e.printStackTrace();
	        } catch (SQLException e) {
	        	System.out.println("Error connecting to MySQL Workbench; Please check account credentials.");
	        	e.printStackTrace();
	        }
	        return null;
	    }
}
