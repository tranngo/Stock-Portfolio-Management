package csci310;

/**
 *	In Register.java we implement all the helper methods we need to
 *	enter a new user into our database. Including hashing passwords,
 *	validating the info entered into the form, and lastly putting the
 *	user and the SHA-256 hash of their password into the database.
 */
public class Register {

	//Check that the username and password are valid
	public static boolean validateUserInfo(String username, String password)
	{
		return true;
	}
	
	//Hash a password with SHA 256
	public static String hashPasswordWithSHA256(String password)
	{
		return "";
	}
	
	//Check that user is not already in database
	public static boolean checkUsernameNotAlreadyTaken(String username)
	{
		return true;
	}
	
	//Put this registration info into the database
	public static boolean stickThisInfoIntoDatabase(String username, String hashed_password)
	{
		return true;
	}
}
