/**
 * 
 */
package csci310;

import static org.junit.Assert.*;
 
import java.security.NoSuchAlgorithmException;

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
	public void testCheckUsernameAlreadyTaken() {
		//The username "serena" is already taken
		String test1 = "serena";
		boolean result = Register.checkUsernameAlreadyTaken(test1);
		assertTrue(result);
		
		//The username "SERENA" is technically not yet taken
		//But we should ask CP if they care about case-sensitivity
		String test2 = "SERENA";
		result = Register.checkUsernameAlreadyTaken(test2);
		assertFalse(result);
		
		//The username "nadal213*" is not yet taken
		String test3 = "nadal213*";
		result = Register.checkUsernameAlreadyTaken(test3);
		assertFalse(result);
	}

	/**
	 * Test method for {@link csci310.Register#stickThisInfoIntoDatabase(java.lang.String, java.lang.String)}.
	 * @throws NoSuchAlgorithmException 
	 */
	@Test
	public void testStickThisInfoIntoDatabase() throws NoSuchAlgorithmException {
		//After putting info into database, we should get "true" back
		//if everything worked
		String test_username = "sharapova415";
		String test_password = "maria45*";
		String hashed_password = Register.hashPasswordWithSHA256(test_password);
		boolean result = Register.stickThisInfoIntoDatabase(test_username, hashed_password);
		assertTrue(result);
	}
	
}
