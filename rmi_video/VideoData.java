/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package rmi_video;

import java.io.File;
import java.io.Serializable;


/**
 * @author Rodrigo Nascimento de Carvalho 380067
 * @author Philippe Cesar Ramos 380415
 */
public class VideoData implements Serializable{
    private final byte[] videoData;
    private final String videoName;
    private final String videoId;
    
    public VideoData(byte[] data, String name, String id){
        this.videoData = data;
        this.videoName = name;
        this.videoId = id;
    }
    
    public byte[] getData(){
        return this.videoData;
    }
    
    public String getFileName(){
        return this.videoName;
    }
    
    public String getId(){
        return this.videoId;
    }
 
}
