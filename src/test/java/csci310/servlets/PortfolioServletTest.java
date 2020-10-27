package csci310.servlets;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.PrintWriter;

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

		when(response.getWriter()).thenReturn(new PrintWriter("Yo"));
		ps.doPost(request, response);


		//Test for null values as input
		when(request.getParameter("startDate")).thenReturn(null);
		when(request.getParameter("endDate")).thenReturn(null);
		when(request.getParameter("externalStocks")).thenReturn(null);
		ps.doPost(request, response);
	}

}
