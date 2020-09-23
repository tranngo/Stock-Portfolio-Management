package csci310;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;

import com.google.common.hash.Hashing;

public class UserInput {
	//Check that the username and password are valid
		public static boolean validateUserInfo(String username, String password)
		{
			ArrayList<String> invalidChars = new ArrayList<String>(
					Arrays.asList("'", "\"", ";"));
			
			// username and password should not be empty strings
			if(username.isEmpty() || password.isEmpty()) {
				return false;
			}
			
			// username and password should not contain invalid characters
			for(String invalidChar: invalidChars) {
				if(username.contains(invalidChar) || password.contains(invalidChar)) {
					return false;
				}
			}
			
			// else, password is valid
			return true;
		}
		
		//Hash a password with SHA 256
		public static String hashPasswordWithSHA256(String password) throws NoSuchAlgorithmException
		{
			// hash password and return as hex string
			String sha256hex = Hashing.sha256()
					  .hashString(password, StandardCharsets.UTF_8)
					  .toString();
			return sha256hex;
		}
}
