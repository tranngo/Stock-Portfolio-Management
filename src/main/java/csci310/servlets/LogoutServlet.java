package csci310.servlets;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/LogoutServlet")
public class LogoutServlet extends HttpServlet {

		/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public LogoutServlet() {
		super();
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		System.out.println("YES: LogoutServlet's doPost was called");
		
		Cookie[] cookies = request.getCookies();
		System.out.println("# of cookies in storage: " + cookies.length);
		for (int i = 0; i < cookies.length; ++i) {
			cookies[i].setMaxAge(0);
			System.out.println("Deleted cookie: " + cookies[i].getName() + " Age: " + cookies[i].getMaxAge());
			response.addCookie(cookies[i]);
		}
		response.setStatus(HttpServletResponse.SC_OK);
		response.sendRedirect("index.html");
		
//		HttpSession session = request.getSession(false);
//		session.removeAttribute("user_id");
//		response.sendRedirect("login.html");
		
	}
}
