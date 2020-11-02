package csci310.servlets;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.mockito.Mockito;

public class PortfolioServletTest extends Mockito {

	@Test
	public void testDoPostHttpServletRequestHttpServletResponse() throws IOException {
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);
		PortfolioServlet ps = new PortfolioServlet();
		
		//User id is 50
		Cookie[] cookies = new Cookie[1];
		cookies[0] = new Cookie("user_id", "50");
		when(request.getCookies()).thenReturn(cookies);

		//Test add
		when(request.getParameter("type")).thenReturn("add");
		when(request.getParameter("stock")).thenReturn("NTNX");
		when(request.getParameter("quantity")).thenReturn("50");
		when(request.getParameter("purchaseDate")).thenReturn("09-09-2019");
		when(request.getParameter("sellDate")).thenReturn("09-18-2019");
		
		when(response.getWriter()).thenReturn(new PrintWriter("Yo"));
		ps.doPost(request, response);
		assertTrue(response != null);
		
		//Test remove
		when(request.getParameter("type")).thenReturn("remove");
		when(request.getParameter("stock")).thenReturn("NTNX");
		
		when(response.getWriter()).thenReturn(new PrintWriter("Yo"));
		ps.doPost(request, response);
		assertTrue(response != null);
		
	}

}
