package csci310.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

@WebServlet("GraphServlet")
public class GraphServlet extends HttpServlet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String jsonArray;
	
	protected void CreateArray() {
		List<List<String>> list = new ArrayList<List<String>>();
		List<String> title = new ArrayList<String>();
		title.add("Year");
		title.add("Sales");
		title.add("Expenses");
		list.add(title);
		
		List<String> data = new ArrayList<String>();
		data.add("2004");
		data.add("1000");
		data.add("404");
		list.add(data);
		
		List<String> data1 = new ArrayList<String>();
		data1.add("2005");
		data1.add("2340");
		data1.add("700");
		list.add(data1);
		
		List<String> data2 = new ArrayList<String>();
		data2.add("2006");
		data2.add("6894");
		data2.add("942");
		list.add(data2);
		
		Gson g = new Gson();
		jsonArray = g.toJson(list);
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		System.out.println("GraphServlet doGet");
		
		CreateArray();
		System.out.println("jsonArray: " + jsonArray);
		
		PrintWriter out;
		out = response.getWriter();
		response.setContentType("application/json");
		out.print(jsonArray);
		out.flush();
		
//		response.setContentType("application/json");
		response.setStatus(HttpServletResponse.SC_OK);
	}
	
	protected String GetArray() {
		return jsonArray;
	}

}
