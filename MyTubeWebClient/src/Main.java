
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import mytube.VideoFile;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Rodrigo
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try{
            Path path = Paths.get("C:\\Users\\Rodrigo\\Documents\\MyTubeVideos\\videoplayback.mp4");
            byte[] data = Files.readAllBytes(path);
            
            String id = uploadVideo("video.mp4", data, "Opening for the Power Rangers TV Show.");
            
            System.out.println(id);
            
            VideoFile f = downloadVideo(id);
            
            System.out.println(f.getDescription());
            
            if(f != null){
                    try (FileOutputStream fos = new FileOutputStream("C:\\Users\\Rodrigo\\Documents\\MyTubeVideos\\videoplayback2.mp4")) {
                        fos.write(f.getData());
                    }
            }
            
        }catch(Exception ex){
            System.err.println(ex);
        }
    }

    private static String uploadVideo(java.lang.String filename, byte[] filedata, java.lang.String filedescription) {
        mytube.MyTubeWS_Service service = new mytube.MyTubeWS_Service();
        mytube.MyTubeWS port = service.getMyTubeWSPort();
        return port.uploadVideo(filename, filedata, filedescription);
    }

    private static VideoFile downloadVideo(java.lang.String id) {
        mytube.MyTubeWS_Service service = new mytube.MyTubeWS_Service();
        mytube.MyTubeWS port = service.getMyTubeWSPort();
        return port.downloadVideo(id);
    }
    
}
