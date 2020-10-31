package csci310.servlets;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.stream.Collectors;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import csci310.Register;
import csci310.JDBC;
import csci310.Login;

public class LoginServlet extends HttpServlet {

	//ALERT: this code is still incomplete, it doesn't redirect the user
		//to a "successful sign up" screen
		@Override
		protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
			
			System.out.println("YES: LoginServlet's doPost was called");
			
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
			
			String username = requestBody.substring(0, firstAnd); //username=wilson103
			String password = requestBody.substring(firstAnd+1); //password=racket
			
			int firstEquals = username.indexOf('=');
			int secondEquals = password.indexOf('=');
			username = username.substring(firstEquals+1); //just "wilson103"
			password = password.substring(secondEquals+1); //just "racket"
			
			boolean userInfoIsValid = Login.checkForLoginCredentials(username, password, "com.mysql.cj.jdbc.Driver", "jdbc:mysql://remotemysql.com:3306/DT6BLiMGub","DT6BLiMGub","W1B4BiSiHP");
			
			//Invalid user info
			if(userInfoIsValid == false) {
				//NOTE: Improve this! Return a response telling the user that they messed up
				System.out.println("User info is not valid");
				//NOTE: If you change this line make sure to fix RegistrationServletTest too
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//				response.sendRedirect("index.html");
				return;
			}
			
			
			//Valid user info, see what user_id we gave them
			// connect to mysql
			int user_id = -1;
			JDBC db = new JDBC();
			Connection con = db.connectDB("com.mysql.cj.jdbc.Driver", "jdbc:mysql://remotemysql.com:3306/DT6BLiMGub","DT6BLiMGub","W1B4BiSiHP");
			
			if(con != null) {
				try {
					
					// query users table for username parameter
					PreparedStatement ps = con.prepareStatement("SELECT id FROM users WHERE username = ?");
					ps.setString(1, username);
					ResultSet rs = ps.executeQuery();
		
					while(rs.next()) {
						user_id = rs.getInt(1);
						System.out.println("Hey! This user has id of " + user_id);
					}
					
		        } catch (SQLException e) {
		        	System.out.println("Error querying user data from DB during registration.");
		        	e.printStackTrace();
		        }
				finally {
				    if (con != null) {
				        try {
				            con.close();
				        } catch (SQLException e) { /* ignored */}
				    }
				}
			}
			
			//Redirect user to the login page
			System.out.println("User info is valid, redirecting to home.html");
			Cookie c = new Cookie("user_id", Integer.toString(user_id) );
			c.setMaxAge(3600);
			response.addCookie(c);
			response.setStatus(HttpServletResponse.SC_OK);
			response.sendRedirect("home.html");
			return;
		}
}
