package videocloud;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;


@SuppressWarnings("serial")
public class StoreVideoDescription extends HttpServlet{
	@Override
	  public void doPost(HttpServletRequest req, HttpServletResponse resp)
	      throws IOException {

		String videoId = req.getParameter("id");
	    Key videoKey = KeyFactory.createKey("VideoId", videoId);
	    String content = req.getParameter("content");
	    Date date = new Date();
	    Entity video = new Entity("Video", videoKey);
	    
	    video.setProperty("date", date);
	    video.setProperty("content", content);

	    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	    datastore.put(video);

	    resp.setContentType("text/plain");
	    resp.getWriter().println("OK");
	  }
}
