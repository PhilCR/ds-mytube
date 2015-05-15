package videocloud;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;

@SuppressWarnings("serial")
public class FetchVideoDescription extends HttpServlet{
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		String videoId = req.getParameter("id");
		

	    Key videoKey = KeyFactory.createKey("VideoId", videoId);
		
	    Query query = new Query("Video", videoKey);
	    Entity video = null;
	    video = datastore.prepare(query).asSingleEntity();
	    
	    if(video == null){
	    	resp.setContentType("text/plain");
			resp.getWriter().println("Video not found");
	    }else{
	    	resp.setContentType("text/plain");
			
			resp.getWriter().println("Video Details");
			resp.getWriter().println(video.getProperty("content"));
			resp.getWriter().println("Created at "+video.getProperty("date"));
	    }
	}
}
