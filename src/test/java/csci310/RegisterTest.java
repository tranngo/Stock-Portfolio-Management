/**
 * 
 */
package csci310;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Scanner;

import org.junit.Test;

/**
 * @author saikalyan
 *
 */
public class RegisterTest {
	
	final private static String DB_CREDENTIALS = "db-credentials.txt";
	
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
			//e.printStackTrace();
			return "";
		}
	}
	
	private static void changePassword(String newPassword) {
		try {
			FileWriter fw = new FileWriter(DB_CREDENTIALS);
			fw.write(newPassword);
			fw.close();
			System.out.println("Debug: Successfully messed up db-credentials.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Error in RegisterTest changing password");
			//e.printStackTrace();
			return;
		}
	}

	/**
	 * Test method for {@link csci310.Register#validateUserInfo(java.lang.String, java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testValidateUserInfo() {
		//Username with single quotes is invalid, we want to prevent SQL injection
		String test_username1 = "CREATE TABLE \'sqlInjectionString\'";
		boolean result = Register.validateUserInfo(test_username1, "pwd", "pwd");
		assertFalse(result);
		
		//Username with double quotes is invalid, we want to prevent SQL injection
		String test_username2 = "CREATE TABLE \"sqlInjectionString\"";
		result = Register.validateUserInfo(test_username2, "pwd", "pwd");
		assertFalse(result);
		
		//Username with semicolon is invalid, we want to prevent SQL injection
		String test_username3 = "; DROP TABLE users";
		result = Register.validateUserInfo(test_username3, "pwd", "pwd");
		assertFalse(result);
		
		//Username with spaces is valid (check with CPs)
		String test_username4 = "Venus Williams";
		result = Register.validateUserInfo(test_username4, "pwd", "pwd");
		assertTrue(result);
		
		//Normal username test is valid
		String test_username5 = "federer34";
		result = Register.validateUserInfo(test_username5, "pwd", "pwd");
		assertTrue(result);
		
		//Empty username is invalid
		String test_username6 = "";
		result = Register.validateUserInfo(test_username6, "pwd", "pwd");
		assertFalse(result);
		
		//Password with single quotes is invalid, we want to prevent SQL injection
		String test_password1 = "CREATE TABLE \'sqlInjectionString\'";
		result = Register.validateUserInfo("usr", test_password1, test_password1);
		assertFalse(result);
		
		//Password with double quotes is invalid, we want to prevent SQL injection
		String test_password2 = "CREATE TABLE \"sqlInjectionString\"";
		result = Register.validateUserInfo("usr", test_password2, test_password2);
		assertFalse(result);
		
		//Password with semicolon is invalid, we want to prevent SQL injection
		String test_password3 = "; DROP TABLE users";
		result = Register.validateUserInfo("usr", test_password3, test_password3);
		assertFalse(result);
		
		//Password with spaces is valid (check with CPs)
		String test_password4 = "secret password";
		result = Register.validateUserInfo("usr", test_password4, test_password4);
		assertTrue(result);
		
		//Normal password test
		String test_password5 = "bunnies314*";
		result = Register.validateUserInfo("usr", test_password5, test_password5);
		assertTrue(result);
		
		//Empty password is invalid
		String test_password6 = "";
		result = Register.validateUserInfo("usr", test_password6, test_password6);
		assertFalse(result);
		
		//Empty username and password is invalid
		String empty_username = "";
		String empty_password = "";
		result = Register.validateUserInfo(empty_username, empty_password, empty_password);
		assertFalse(result);
		
		//Password and confirm password not matching is invalid
		String pass = "racket";
		String different_pass = "racket34";
		result = Register.validateUserInfo("usr", pass, different_pass);
		assertFalse(result);
	}

	/**
	 * Test method for {@link csci310.Register#hashPasswordWithSHA256(java.lang.String)}.
	 * @throws NoSuchAlgorithmException 
	 */
	@Test
	public void testHashPasswordWithSHA256() throws NoSuchAlgorithmException {
		//Hash the password "password123"
		String test_password1 = "password123";
		String result = Register.hashPasswordWithSHA256(test_password1);
		assertEquals(result, "ef92b778bafe771e89245b89ecbc08a44a4e166c06659911881f383d4473e94f");
		
		//Hash the password "secret password" (with spaces)
		String test_password2 = "secret password";
		result = Register.hashPasswordWithSHA256(test_password2);
		assertEquals(result, "1ba133eccdfc4e5ca3405dfd70c11360af038106c9eebdde504a4b14c94b8557");
		
		//Hash the password "bunnies314*" (with special characters)
		String test_password3 = "bunnies314*";
		result = Register.hashPasswordWithSHA256(test_password3);
		assertEquals(result, "9656f9d5e1edd3d613aa58b075f7b182ec306775d786fcfbb0e792a877ad002e");
		
		//Hash the password "" (empty password)
		String test_password4 = "";
		result = Register.hashPasswordWithSHA256(test_password4);
		assertEquals(result, "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855");
	}

	/**
	 * Test method for {@link csci310.Register#checkUsernameNotAlreadyTaken(java.lang.String)}.
	 * @throws SQLException 
	 */
	@Test
	public void testCheckUserExists() throws SQLException {
	
		//The username "serena" is already taken
		String test1 = "serena";
		boolean result = Register.checkUserExists(test1);
		assertTrue(result);
		
		//The username "SERENA" is technically not yet taken
		//But we should ask CP if they care about case-sensitivity
		String test2 = "SERENA";
		result = Register.checkUserExists(test2);
		assertFalse(result);
		
		//The username "nadal213*" is not yet taken
		String test3 = "nadal213*";
		result = Register.checkUserExists(test3);
		assertFalse(result);
		
		//Cobertura coverage: Disable MySQL with a bad password so con=null
		//Code to read file referenced from W3Schools
		
		//Retrieve password from "db-credentials.txt"
		File myFile = new File(DB_CREDENTIALS);
		String password = getPassword(myFile);
		
		//Write a string to mess up "db-credentials.txt"
		changePassword("Messing up your password mwahaha");
		
		//Try checking DB now, it won't work since your password is wrong
		String test4 = "nadal213*";
		result = Register.checkUserExists(test4);
		
		//Fix "db-credentials.txt" by putting the right password back
		changePassword(password);
		
		//COVERAGE THROWING EXCEPTION
		JDBC connection = mock(JDBC.class);
		when(connection.connectDB()).thenReturn(null);
		result = Register.checkUserExists("mockito");
		assertFalse(result);
	}

	/**
	 * Test method for {@link csci310.Register#stickThisInfoIntoDatabase(java.lang.String, java.lang.String)}.
	 * @throws NoSuchAlgorithmException 
	 */
	@Test
	public void testInsertUser() throws NoSuchAlgorithmException {
		//After putting info into database, we should get "true" back
		//if everything worked
		Register r = new Register();
		String test_username = "sharapova415";
		String test_password = "maria45*";
		String hashed_password = Register.hashPasswordWithSHA256(test_password);
		boolean result = Register.insertUser(test_username, hashed_password);
		assertTrue(result);
		
		//Cobertura coverage: Disable MySQL with a bad password so con=null
		//Code to read file referenced from W3Schools
		
		//Retrieve password from "db-credentials.txt"
		File myFile = new File(DB_CREDENTIALS);
		String password = getPassword(myFile);
		
		//Write a string to mess up "db-credentials.txt"
		changePassword("Messing up your password mwahaha");
		
		//Try using DB now, it won't work since password is wrong
		result = Register.insertUser(test_username, hashed_password);
		
		//Fix "db-credentials.txt" by putting the right password back
		changePassword(password);
	}
}