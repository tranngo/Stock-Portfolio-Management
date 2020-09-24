package csci310;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;

import com.google.common.hash.Hashing;

public class UserInput {
	// Check the validity of username and password
	public static boolean validateUserInfo(String username, String password) {
		ArrayList<String> invalidChars = new ArrayList<String>(Arrays.asList("'", "\"", ";"));
		
		// username and password should not be empty strings
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
}
