/**
 * 
 */
package csci310.servlets;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mockito.Mockito;

/**
 * @author saikalyan
 *
 */
public class RegistrationServletTest extends Mockito {

	/**
	 * Test method for {@link csci310.servlets.RegistrationServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)}.
	 */
	@Test
	public void testDoGetHttpServletRequestHttpServletResponse() {
		
		//For "Get", request and response are blank since there are
		//no parameters that we are asking for from the user
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);
		
		//With a blank request and response, call doGet
		RegistrationServlet rs = new RegistrationServlet();
		try {
			rs.doGet(request, response);
		} catch (IOException e) {
			System.out.println("ERROR with RegistrationServletTest doGet() test" );
			e.printStackTrace();
		}
		
		//Check that the request is successful (status code is 200)
		assertEquals(response.getStatus(), 200);
		
	}

	/**
	 * Test method for {@link csci310.servlets.RegistrationServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)}.
	 */
	@Test
	public void testDoPostHttpServletRequestHttpServletResponse() {
		//For "Post", we need the username and password to be faked
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);
		
		//Mock a username and password for the request
		when(request.getParameter("username")).thenReturn("Professor34");
		when(request.getParameter("password")).thenReturn("testPassword543");
		
		//With a blank request and response, call doGet
		RegistrationServlet rs = new RegistrationServlet();
		try {
			rs.doPost(request, response);
		} catch (IOException e) {
			System.out.println("ERROR with RegistrationServletTest doPost() test" );
			e.printStackTrace();
		}
		
		//Check that the request is successful (status code is 200)
		//NOTE: Later on we should probably check whether the entry
		//actually got placed in the database
		assertEquals(response.getStatus(), 200);
	}

}
