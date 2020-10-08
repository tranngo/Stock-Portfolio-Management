package csci310.servlets;

import static org.junit.Assert.assertTrue;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.mockito.Mockito;

public class GraphServletTest extends Mockito {

	@Test
	public void testDoGetHttpServletRequestHttpServletResponse() {
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);
		GraphServlet gs = new GraphServlet();
		gs.CreateArray();
		String result = gs.GetArray();
		System.out.println(result);
		assertTrue(!result.isEmpty());
//		fail("Not yet implemented");
	}

}
