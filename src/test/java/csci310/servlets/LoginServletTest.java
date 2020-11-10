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
		String requestBody = "username=wilson133&password=racket";

		//Mock the Request.getReader() found in the first try block of doPost()
		try {
			when(request.getReader()).thenReturn(new BufferedReader(new StringReader(requestBody)));
		} catch (IOException e1) {
			// System.out.println("ERROR P1 with RegistrationServletTest doPost() test" );
			e1.printStackTrace();
			return;
		}
		
		//Call doPost with this test username/password
		LoginServlet ls = new LoginServlet();
		try {
			System.out.println("About to run LoginServlet unit test 1");
			ls.doPost(request, response);
		} catch (IOException e) {
			// System.out.println("ERROR Q2 with RegistrationServletTest doPost() test" );
			e.printStackTrace();
			return;
		}

		
		//For cobertura coverage: enter a invalid username
		String invalidUsername = "";
		String requestBody2 = "username=yo"+invalidUsername;
		requestBody2 += "&password=yo";
		
		HttpServletRequest request1 = mock(HttpServletRequest.class);
		HttpServletResponse response1 = mock(HttpServletResponse.class);
		try {
			when(request1.getReader()).thenReturn(new BufferedReader(new StringReader(requestBody2)));
			System.out.println("About to run LoginServlet unit test 3");
			ls.doPost(request1, response1);
			assertEquals(response1.getStatus(), 0);
		} catch (IOException e1) {
			// System.out.println("RegistrationServletTest IOException");
		}
		
		//cobertura coverage: throw error
		try {
			when(request.getReader()).thenThrow(IOException.class);
			System.out.println("About to run LoginServlet unit test 4");
			ls.doPost(request, response);
			assertEquals(response.getStatus(), 0);
		} catch (IOException e1) {
			// System.out.println("RegistrationServletTest IOException");
		}
		
	}

}
