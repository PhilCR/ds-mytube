package videocloud;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@SuppressWarnings("serial")
public class GeraId extends HttpServlet{
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
		UUID uniqueVideoId = UUID.randomUUID();
		
		resp.setContentType("text/plain");
		resp.getWriter().println(String.valueOf(uniqueVideoId));
	}
}
