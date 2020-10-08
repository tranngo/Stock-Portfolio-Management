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
public class UserInputTest {

	/**
	 * Test method for {@link csci310.UserInput#validateUserInfo(java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testValidateUserInfo() {
		//Just for coverage
		UserInput userinput = new UserInput();
		
		//Try empty username
		String usr = "";
		String pwd = "Yo";
		boolean result = UserInput.validateUserInfo(usr, pwd);
		assertFalse(result);
		
		//Try empty password
		usr = "Hi";
		pwd = "";
		result = UserInput.validateUserInfo(usr, pwd);
		assertFalse(result);
		
		//Try username with invalid char
		usr = "invalid_username;";
		pwd = "valid_password";
		result = UserInput.validateUserInfo(usr, pwd);
		assertFalse(result);
		
		//Try password with invalid char
		usr = "valid_username";
		pwd = "invalid_password;";
		result = UserInput.validateUserInfo(usr, pwd);
		assertFalse(result);
		
		//Valid username and password
		usr = "valid_username";
		pwd = "valid_password";
		result = UserInput.validateUserInfo(usr, pwd);
		assertTrue(result);
	}

	/**
	 * Test method for {@link csci310.UserInput#hashPasswordWithSHA256(java.lang.String)}.
	 */
	@Test
	public void testHashPasswordWithSHA256() {
		try {
			String result = UserInput.hashPasswordWithSHA256("racket");
			assertEquals("b51534ce9aa94a79928d1934986518fae0f15fd5b9ed4694dffb213104b39257", result);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
