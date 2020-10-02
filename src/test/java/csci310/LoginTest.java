/**
 * 
 */
package csci310;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

/**
 * @author tran
 *
 */
public class LoginTest {
	
	final private static String DB_CREDNETIALS = "db-credentials.txt";
	
	private static String getPassword(File myFile) {
		String password = "N/A";
		try {
			Scanner myScanner = new Scanner(myFile);
			while(myScanner.hasNextLine()) {
				password = myScanner.nextLine();
			}
			myScanner.close();
			return password;
		} catch (FileNotFoundException e) {
			System.out.println("Error in RegisterTest getting password");
			e.printStackTrace();
			return "";
		}
	}
	
	private static void changePassword(String newPassword) {
		try {
			FileWriter fw = new FileWriter(DB_CREDNETIALS);
			fw.write(newPassword);
			fw.close();
			System.out.println("Debug: Successfully messed up db-credentials.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void testCheckForLoginCredentials() {
		
		//Instantiate Login object, we don't actually use it, but for coverage purposes
		Login test = new Login();
		
		//Retrieve password from "db-credentials.txt"
		File myFile = new File(DB_CREDNETIALS);
		String password = getPassword(myFile);
		
		//Write a string to mess up "db-credentials.txt"
		changePassword("Messing up your password mwahaha");
		
		//Try using DB now, it won't work since password is wrong
		Login.checkForLoginCredentials("usr", "pwd");
		
		//Fix "db-credentials.txt" by putting the right password back
		changePassword(password);
		
		// Checking invalid login credentials - should return false
		String username1 = "h2727dhbcbs";
		String password1 = "skm2772hwml";
		boolean result = Login.checkForLoginCredentials(username1, password1);
		assertFalse(result);
		
		// Checking valid login credentials - should return true
		// Login and username comes from testInsertUser() of RegisterTest.java
		String username2 = "sharapova415";
		String password2 = "maria45*";
		result = Login.checkForLoginCredentials(username2, password2);
		assertTrue(result);
	}

}
