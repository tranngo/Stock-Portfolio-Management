package csci310.servlets;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.mockito.Mockito;

public class GraphServletTest extends Mockito {

	@Test
	public void testDoGetHttpServletRequestHttpServletResponse() throws IOException {
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);
		GraphServlet gs = new GraphServlet();
		gs.CreateArray();
		when(response.getWriter()).thenReturn(new PrintWriter("Yo"));
		gs.doGet(request, response);
		String result = gs.GetArray();
		System.out.println(result);
		assertTrue(!result.isEmpty());
		
		when(request.getParameter("startDate")).thenReturn(null);
		when(request.getParameter("endDate")).thenReturn(null);
		when(request.getParameter("externalStocks")).thenReturn(null);
		gs.doGet(request, response);
	}

}