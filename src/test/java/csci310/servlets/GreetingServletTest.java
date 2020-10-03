/**
 * 
 */
package csci310.servlets;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mockito.Mockito;

import org.junit.Test;

/**
 * @author saikalyan
 *
 */
public class GreetingServletTest extends Mockito {

	/**
	 * Test method for {@link csci310.servlets.GreetingServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)}.
	 */
	@Test
	public void testDoGetHttpServletRequestHttpServletResponse() {
		//For "Get", we need the username and password to be faked
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);
		
		//NOTE: THIS PART MIGHT CAUSE A PROBLEM
		//Mock the Response.getWriter() found in the last line of Get()
		try {
			when(response.getWriter()).thenReturn(new PrintWriter("Yo"));
		} catch (IOException e1) {
			System.out.println("ERROR P1 with RegistrationServletTest doPost() test" );
			e1.printStackTrace();
			return;
		}
		
		//Call doGet
		GreetingServlet gs = new GreetingServlet();
		try {
			gs.doGet(request, response);
		} catch (IOException e) {
			System.out.println("ERROR Q2 with RegistrationServletTest doPost() test" );
			e.printStackTrace();
			return;
		}
	}

}
