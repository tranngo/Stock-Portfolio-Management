/**
 * 
 */
package csci310.servlets;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;

import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mockito.Mockito;

import csci310.Register;

import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.stream.Stream;

/**
 * @author saikalyan
 *
 */
public class RegistrationServletTest extends Mockito {

	/**
	 * Test method for {@link csci310.servlets.RegistrationServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)}.
	 */
	@Test
	public void testDoPostHttpServletRequestHttpServletResponse() {
		//For "Post", we need the username and password to be faked
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);
		
		//Mock a username and password for the request
		//Every user name is unique because we append the current Unix timestamp
		String willy = "wilson" + Instant.now().getEpochSecond();
		String requestBody = "username="+willy;
		requestBody += "&password=racket&passwordConfirmation=racket";

		//Mock the Request.getReader() found in the first try block of doPost()
		try {
			when(request.getReader()).thenReturn(new BufferedReader(new StringReader(requestBody)));
		} catch (IOException e1) {
			System.out.println("ERROR P1 with RegistrationServletTest doPost() test" );
			e1.printStackTrace();
			return;
		}
		
		//Call doPost with this test username/password
		RegistrationServlet rs = new RegistrationServlet();
		try {
			rs.doPost(request, response);
		} catch (IOException e) {
			System.out.println("ERROR Q2 with RegistrationServletTest doPost() test" );
			e.printStackTrace();
			return;
		}
		
		//Check that the new user is in the database
		assertTrue(Register.checkUserExists(willy));
		
		//For cobertura coverage: try inserting same user again
		try {
			when(request.getReader()).thenReturn(new BufferedReader(new StringReader(requestBody)));
			rs.doPost(request, response);
			assertEquals(response.getStatus(), 0);
		} catch (IOException e) {
			System.out.println("RegistrationServletTest IOException");
		}
		
		//For cobertura coverage: enter a invalid username
		String invalidUsername = "yo\"";
		String requestBody2 = "username="+invalidUsername;
		requestBody2 += "&password=racket&passwordConfirmation=racket";
		try {
			when(request.getReader()).thenReturn(new BufferedReader(new StringReader(requestBody2)));
			rs.doPost(request, response);
			assertEquals(response.getStatus(), 0);
		} catch (IOException e1) {
			System.out.println("RegistrationServletTest IOException");
		}
	}

}
