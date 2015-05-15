/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package rmi_video;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bson.types.ObjectId;

/**
 * @author Rodrigo Nascimento de Carvalho 380067
 * @author Philippe Cesar Ramos 380415
 */
public class VideoServer implements VideoInterface{

    public VideoServer(){} 

    @Override
    public boolean addVideo(VideoData d) throws RemoteException {
        try {
            //Conexao com mongoDB
            MongoClient mongoClient = new MongoClient("localhost", 27017);
            DB db = mongoClient.getDB("VideoDatabase");
            
            
            try (FileOutputStream fos = new FileOutputStream("/Users/philcr/Documents/"+d.getFileName())) {
                fos.write(d.getData());
            }
            
            
            File videoFile = new File("/Users/philcr/Documents/"+d.getFileName());
            
            GridFS gfsVideo = new GridFS(db, "video");
            
            //Cria e salva o arquivo no DB pelo GridFS
            GridFSInputFile gfsFile = gfsVideo.createFile(videoFile);
            gfsFile.setId(new ObjectId()); //Utiliza a criação de ID do mongo
            gfsFile.put("videoId", d.getId()); //Utiliza nosso metodo de ID
            gfsFile.setFilename(d.getFileName());
            gfsFile.save();
            
            //Exclui o arquivo local
            boolean deletedFlag = videoFile.delete();
            if(!deletedFlag){
                System.err.println("File could not be deleted!");
            }
            
            mongoClient.close();
            
            return true;
            
            
        } catch (UnknownHostException ex) {
            Logger.getLogger(VideoServer.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(VideoServer.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (IOException ex) {
            Logger.getLogger(VideoServer.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public VideoData getVideo(String id) throws RemoteException {
        try {
            //Cria conexao com MongoDB
            MongoClient mongoClient = new MongoClient("localhost", 27017);
            DB db = mongoClient.getDB("VideoDatabase");
            
            //Recupera o video atraves do ID
            GridFS gfsVideo = new GridFS(db, "video");
            BasicDBObject whereQuery = new BasicDBObject();
            whereQuery.put("videoId", id);
            GridFSDBFile videoForOutput = gfsVideo.findOne(whereQuery);
            
            String filename = videoForOutput.getFilename();
            
            try (FileOutputStream fos = new FileOutputStream("/Users/philcr/Documents/"+filename)) {
                videoForOutput.writeTo(fos);
            }
            
            Path path = Paths.get("/Users/philcr/Documents/"+filename);
            byte[] data = Files.readAllBytes(path);    
            
            File videoFile = new File("/Users/philcr/Documents/"+filename);
            
            //Exclui o arquivo local
            boolean deletedFlag = videoFile.delete();
            if(!deletedFlag){
                System.err.println("Video could not be deleted!");
            }
            
            mongoClient.close();
            return new VideoData(data, filename, id);
        } catch (UnknownHostException ex) {
            Logger.getLogger(VideoServer.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }catch (FileNotFoundException ex) {
            Logger.getLogger(VideoServer.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (IOException ex) {
            Logger.getLogger(VideoServer.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    
    
    
}
