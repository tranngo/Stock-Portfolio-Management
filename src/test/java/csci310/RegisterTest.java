/**
 * 
 */
package csci310;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Scanner;

import org.junit.Test;

/**
 * @author saikalyan
 *
 */
public class RegisterTest {

	/**
	 * Test method for {@link csci310.Register#validateUserInfo(java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testValidateUserInfo() {
		//Username with single quotes is invalid, we want to prevent SQL injection
		String test_username1 = "CREATE TABLE \'sqlInjectionString\'";
		boolean result = Register.validateUserInfo(test_username1, "pwd");
		assertFalse(result);
		
		//Username with double quotes is invalid, we want to prevent SQL injection
		String test_username2 = "CREATE TABLE \"sqlInjectionString\"";
		result = Register.validateUserInfo(test_username2, "pwd");
		assertFalse(result);
		
		//Username with semicolon is invalid, we want to prevent SQL injection
		String test_username3 = "; DROP TABLE users";
		result = Register.validateUserInfo(test_username3, "pwd");
		assertFalse(result);
		
		//Username with spaces is valid (check with CPs)
		String test_username4 = "Venus Williams";
		result = Register.validateUserInfo(test_username4, "pwd");
		assertTrue(result);
		
		//Normal username test is valid
		String test_username5 = "federer34";
		result = Register.validateUserInfo(test_username5, "pwd");
		assertTrue(result);
		
		//Empty username is invalid
		String test_username6 = "";
		result = Register.validateUserInfo(test_username6, "pwd");
		assertFalse(result);
		
		//Password with single quotes is invalid, we want to prevent SQL injection
		String test_password1 = "CREATE TABLE \'sqlInjectionString\'";
		result = Register.validateUserInfo("usr", test_password1);
		assertFalse(result);
		
		//Password with double quotes is invalid, we want to prevent SQL injection
		String test_password2 = "CREATE TABLE \"sqlInjectionString\"";
		result = Register.validateUserInfo("usr", test_password2);
		assertFalse(result);
		
		//Password with semicolon is invalid, we want to prevent SQL injection
		String test_password3 = "; DROP TABLE users";
		result = Register.validateUserInfo("usr", test_password3);
		assertFalse(result);
		
		//Password with spaces is valid (check with CPs)
		String test_password4 = "secret password";
		result = Register.validateUserInfo("usr", test_password4);
		assertTrue(result);
		
		//Normal password test
		String test_password5 = "bunnies314*";
		result = Register.validateUserInfo("usr", test_password5);
		assertTrue(result);
		
		//Empty password is invalid
		String test_password6 = "";
		result = Register.validateUserInfo("usr", test_password6);
		assertFalse(result);
		
		//Empty username and password is invalid
		String empty_username = "";
		String empty_password = "";
		result = Register.validateUserInfo(empty_username, empty_password);
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
	 */
	@Test
	public void testCheckUserExists() {
		//The username "serena" is already taken
		String test1 = "serena";
		boolean result = false;
		try {
			result = Register.checkUserExists(test1);
		} catch (ClassNotFoundException | SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		assertTrue(result);
		
		//The username "SERENA" is technically not yet taken
		//But we should ask CP if they care about case-sensitivity
		String test2 = "SERENA";
		try {
			result = Register.checkUserExists(test2);
		} catch (ClassNotFoundException | SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		assertFalse(result);
		
		//The username "nadal213*" is not yet taken
		String test3 = "nadal213*";
		try {
			result = Register.checkUserExists(test3);
		} catch (ClassNotFoundException | SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		assertFalse(result);
		
		//Cobertura coverage: Disable MySQL with a bad password so con=null
		//Code to read file referenced from W3Schools
		
		//Retrieve password from "db-credentials.txt"
		File myFile = new File("db-credentials.txt");
		String password = "N/A";
		try {
			Scanner myScanner = new Scanner(myFile);
			while(myScanner.hasNextLine()) {
				password = myScanner.nextLine();
			}
			myScanner.close();
		} catch (FileNotFoundException e) {
			System.out.println("Error in RegisterTest testCheckUserExists");
			e.printStackTrace();
		}
		
		//Write a string to mess up "db-credentials.txt"
		try {
			FileWriter fw = new FileWriter("db-credentials.txt");
			fw.write("Messing up your password mwahaha");
			fw.close();
			System.out.println("Debug: Successfully messed up db-credentials.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Try checking DB now, it won't work since your password is wrong
		String test4 = "nadal213*";
		try {
			result = Register.checkUserExists(test4);
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		//Fix "db-credentials.txt" by putting the right password back
		try {
			FileWriter fw2 = new FileWriter("db-credentials.txt");
			fw2.write(password);
			fw2.close();
			System.out.println("Debug: Successfully fixed db-credentials.txt :)");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Register r = new Register();
	}

	/**
	 * Test method for {@link csci310.Register#stickThisInfoIntoDatabase(java.lang.String, java.lang.String)}.
	 * @throws NoSuchAlgorithmException 
	 */
	@Test
	public void testInsertUser() throws NoSuchAlgorithmException {
		//After putting info into database, we should get "true" back
		//if everything worked
		String test_username = "sharapova415";
		String test_password = "maria45*";
		String hashed_password = Register.hashPasswordWithSHA256(test_password);
		boolean result = false;
		try {
			result = Register.insertUser(test_username, hashed_password);
		} catch (ClassNotFoundException | SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		assertTrue(result);
		
		//Cobertura coverage: Disable MySQL with a bad password so con=null
		//Code to read file referenced from W3Schools
		
		//Retrieve password from "db-credentials.txt"
		File myFile = new File("db-credentials.txt");
		String password = "N/A";
		try {
			Scanner myScanner = new Scanner(myFile);
			while(myScanner.hasNextLine()) {
				password = myScanner.nextLine();
			}
			myScanner.close();
		} catch (FileNotFoundException e) {
			System.out.println("Error in RegisterTest testCheckUserExists");
			e.printStackTrace();
		}
		
		//Write a string to mess up "db-credentials.txt"
		try {
			FileWriter fw = new FileWriter("db-credentials.txt");
			fw.write("Messing up your password mwahaha");
			fw.close();
			System.out.println("Debug: Successfully messed up db-credentials.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Try using DB now, it won't work since password is wrong
		try {
			result = Register.insertUser(test_username, hashed_password);
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		//Fix "db-credentials.txt" by putting the right password back
		try {
			FileWriter fw2 = new FileWriter("db-credentials.txt");
			fw2.write(password);
			fw2.close();
			System.out.println("Debug: Successfully fixed db-credentials.txt :)");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
