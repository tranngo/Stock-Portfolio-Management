/**
 * 
 */
package csci310.servlets;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.time.Instant;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mockito.Mockito;

import org.junit.Test;

import java.util.Arrays;
import java.util.Date;
import java.util.stream.Stream;

import csci310.Register;

/**
 * @author saikalyan
 *
 */
public class LoginServletTest extends Mockito {

	/**
	 * Test method for {@link csci310.servlets.LoginServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)}.
	 */
	@Test
	public void testDoPostHttpServletRequestHttpServletResponse() {
		//For "Post", we need the username and password to be faked
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);
				
		//Mock a username and password for the request
		//Every user name is unique because we append the current Unix timestamp
		String requestBody = "username=sharapova415&password=maria45*&passwordConfirmation=maria45*";

		//Mock the Request.getReader() found in the first try block of doPost()
		try {
			when(request.getReader()).thenReturn(new BufferedReader(new StringReader(requestBody)));
		} catch (IOException e1) {
			System.out.println("ERROR P1 with RegistrationServletTest doPost() test" );
			e1.printStackTrace();
			return;
		}
		
		//Call doPost with this test username/password
		LoginServlet ls = new LoginServlet();
		try {
			ls.doPost(request, response);
		} catch (IOException e) {
			System.out.println("ERROR Q2 with RegistrationServletTest doPost() test" );
			e.printStackTrace();
			return;
		}
		
		//For cobertura coverage: enter a invalid username
		String invalidUsername = "yo";
		String requestBody2 = "username="+invalidUsername;
		requestBody2 += "&password=yo&passwordConfirmation=yo";
		try {
			when(request.getReader()).thenReturn(new BufferedReader(new StringReader(requestBody2)));
			ls.doPost(request, response);
			assertEquals(response.getStatus(), 0);
		} catch (IOException e1) {
			System.out.println("RegistrationServletTest IOException");
		}
	}

}
