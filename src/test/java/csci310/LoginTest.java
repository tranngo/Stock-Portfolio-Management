/**
 * 
 */
package csci310;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.security.NoSuchAlgorithmException;

import org.junit.Before;
import org.junit.Test;

/**
 * @author tran
 *
 */
public class LoginTest {
	
//	private UserInput userInput;
//	
//	@Before
//	public void setup() {
//		userInput = new UserInput();
//	}
	
	@Test
	public void testValidateUserInfo() {		
		// Username and password are empty strings - should return false
		String username1 = "";
		String password1 = "";
		boolean result = UserInput.validateUserInfo(username1, password1);
		assertFalse(result);
		
		// Username is valid and password is an empty string - should return false
		String username2 = "user";
		String password2 = "";
		result = UserInput.validateUserInfo(username2, password2);
		assertFalse(result);
		
		// Username is an empty string and password is valid - should return false
		String username3 = "";
		String password3 = "password123";
		result = UserInput.validateUserInfo(username3, password3);
		assertFalse(result);
		
		// Username contains invalid character and password contains invalid character - should return false
		String username4 = "user;";
		String password4 = "password;";
		result = UserInput.validateUserInfo(username4, password4);
		assertFalse(result);
		
		// Username is valid and password contains invalid character - should return false
		String username5 = "user";
		String password5 = "password;";
		result = UserInput.validateUserInfo(username5, password5);
		assertFalse(result);
		
		// Username contains invalid character and password is valid - should return false
		String username6 = "user;";
		String password6 = "password;";
		result = UserInput.validateUserInfo(username6, password6);
		assertFalse(result);
		
		// Username and password are valid - should return true
		String username7 = "user";
		String password7 = "password";
		result = UserInput.validateUserInfo(username7, password7);
		assertTrue(result);	
	}
	
	@Test
	public void testHashPasswordWithSHA256() throws NoSuchAlgorithmException {
		// Hash the password "password123"
		String test_password1 = "password123";
		String result = UserInput.hashPasswordWithSHA256(test_password1);
		assertEquals(result, "ef92b778bafe771e89245b89ecbc08a44a4e166c06659911881f383d4473e94f");
		
		// Hash the password "secret password" (with spaces)
		String test_password2 = "secret password";
		result = UserInput.hashPasswordWithSHA256(test_password2);
		assertEquals(result, "1ba133eccdfc4e5ca3405dfd70c11360af038106c9eebdde504a4b14c94b8557");
		
		// Hash the password "bunnies314*" (with special characters)
		String test_password3 = "bunnies314*";
		result = UserInput.hashPasswordWithSHA256(test_password3);
		assertEquals(result, "9656f9d5e1edd3d613aa58b075f7b182ec306775d786fcfbb0e792a877ad002e");
		
		// Hash the password "" (empty password)
		String test_password4 = "";
		result = UserInput.hashPasswordWithSHA256(test_password4);
		assertEquals(result, "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855");
	}
	
	@Test
	public void testCheckForLoginCredentials() {
//		// Checking invalid login credentials - should return false
//		String username1 = "h2727dhbcbs";
//		String password1 = "skm2772hwml";
//		boolean result = Login.checkForLoginCredentials(username1, password1);
//		assertFalse(result);
//		
//		// Checking valid login credentials - should return true
//		// Login and username comes from testInsertUser() of RegisterTest.java
//		String username2 = "sharapova415";
//		String password2 = "maria45*";
//		result = Login.checkForLoginCredentials(username2, password2);
//		assertTrue(result);
	}

}
