package csci310.servlets;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import csci310.Register;

public class RegistrationServlet extends HttpServlet {
	
	//ALERT: this code is still incomplete, it doesn't redirect the user
	//to a "successful sign up" screen
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		System.out.println("YES: RegistrationServlet's doPost was called");
		
		//Code referenced from the URL shortener demo
		String requestBody;
		
		try {
			//Add together all the pairs of lines in the request
			//So then we can read the JSON out of the request and do something useful with GSON library
			requestBody = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
		} catch (IOException e) {
			e.printStackTrace();
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return;
		}
		System.out.println("Debug: requestBody is: " + requestBody);
		
		//NOTE: requestBody looks like "username=wilson103&password=racket&passwordConfirmation=racket"
		
		//At this point we have properly read the request body into a String object
		//Now we will read the form data the user has sent to us
		//i.e. the username and password they entered
		
		//Some parsing code to extract the username and password from the above string
		int firstAnd = requestBody.indexOf('&');
		int secondAnd = requestBody.lastIndexOf('&');
		
		String username = requestBody.substring(0, firstAnd); //username=wilson103
		String password = requestBody.substring(firstAnd+1, secondAnd); //password=racket
		
		int firstEquals = username.indexOf('=');
		int secondEquals = password.indexOf('=');
		username = username.substring(firstEquals+1); //just "wilson103"
		password = password.substring(secondEquals+1); //just "racket"
		
		boolean userInfoIsValid = Register.validateUserInfo(username, password);
		
		//Invalid user info
		if(userInfoIsValid == false) {
			//NOTE: Improve this! Return a response telling the user that they messed up
			System.out.println("User info is not valid");
			//NOTE: If you change this line make sure to fix RegistrationServletTest too
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return;
		}
		
		//User already in database
		boolean userAlreadyInDatabase = Register.checkUserExists(username);
		
		if(userAlreadyInDatabase == true) {
			//NOTE: Improve this! Return a response saying the user already is registered
			System.out.println("User already in database");
			//NOTE: If you change this line make sure to fix RegistrationServletTest too
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return;
		}
		
		//Hash the password
		String hashed_password = "";
		hashed_password = Register.hashPasswordWithSHA256(password);
		
		
		//Put the user in the database, everything is okay!
		Register.insertUser(username, hashed_password);
		System.out.println("YES: A new user was successfully added to the database!");
		
		//Redirect user to the login page
		response.setStatus(HttpServletResponse.SC_OK);
		response.sendRedirect("login.html");
		return;
	}
}
