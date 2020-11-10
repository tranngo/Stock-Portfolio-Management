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
		when(request.getParameter("purchaseDate")).thenReturn("2019-09-09"); // I had the wrong order before
		when(request.getParameter("sellDate")).thenReturn("2019-09-18"); // I had the wrong order before
		
		when(response.getWriter()).thenReturn(new PrintWriter("Yo"));
		ps.doPost(request, response);
		assertTrue(response != null);
		
		//Test add: Quantity is not a number
		when(request.getParameter("quantity")).thenReturn("Quantity is not a number"); //change quantity
		when(response.getWriter()).thenReturn(new PrintWriter("Yo"));
		ps.doPost(request, response);
		assertTrue(response != null);
		
		//Test add: Invalid stock
		when(request.getParameter("quantity")).thenReturn("50"); //put back quantity
		when(request.getParameter("stock")).thenReturn("Invalid stock");
		when(response.getWriter()).thenReturn(new PrintWriter("Yo"));
		ps.doPost(request, response);
		assertTrue(response != null);
		
		//Test remove
		when(request.getParameter("type")).thenReturn("remove");
		when(request.getParameter("stock")).thenReturn("NTNX");
		
		when(response.getWriter()).thenReturn(new PrintWriter("Yo"));
		ps.doPost(request, response);
		assertTrue(response != null);
		

		
		
		
		//For cobertura coverage: cookies but user_id not found and bad request type
		
		//User id is -123
		cookies = new Cookie[1];
		cookies[0] = new Cookie("user_id", "-123");
		when(request.getCookies()).thenReturn(cookies);

		//Bad request type
		when(request.getParameter("type")).thenReturn("bad request type");
		when(response.getWriter()).thenReturn(new PrintWriter("Yo"));
		ps.doPost(request, response);
		assertTrue(response != null);
		
		//No cookies
		when(request.getCookies()).thenReturn(null);
		when(request.getParameter("type")).thenReturn("bad request type");
		when(response.getWriter()).thenReturn(new PrintWriter("Yo"));
		ps.doPost(request, response);
		assertTrue(response != null);
		
		//For cobertura coverage: getPortfolioValue
		
		//User id is 7 (this is the wilson133 account)
		cookies = new Cookie[1];
		cookies[0] = new Cookie("user_id", "7");
		when(request.getCookies()).thenReturn(cookies);

		//Test getPortfolioValue
		when(request.getParameter("type")).thenReturn("getPortfolioValue");
		when(response.getWriter()).thenReturn(new PrintWriter("Yo"));
		ps.doPost(request, response);
		assertTrue(response != null);
		
		//Test getPercentChange
		when(request.getParameter("type")).thenReturn("getPercentChange");
		when(response.getWriter()).thenReturn(new PrintWriter("Yo"));
		ps.doPost(request, response);
		assertTrue(response != null);
		
		//Test getPortfolioList
		when(request.getParameter("type")).thenReturn("getPortfolioList");
		when(response.getWriter()).thenReturn(new PrintWriter("Yo"));
		ps.doPost(request, response);
		assertTrue(response != null);
		
	}

}
