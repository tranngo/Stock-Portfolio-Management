package csci310.servlets;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.mockito.Mockito;

public class LogoutServletTest extends Mockito{

	@Test
	public void testDoPostHttpServletRequestHttpServletResponse() throws IOException {
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);
		LogoutServlet ls = new LogoutServlet();

		Cookie[] cookies = new Cookie[1];
		cookies[0] = new Cookie("user_id", "15");
		
		when(request.getCookies()).thenReturn(cookies);
		ls.doPost(request, response);
		Cookie[] c = request.getCookies();
		assertTrue(c[0].getMaxAge() == 0);
		
	}

}
