/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.


@author Rodrigo Nascimento de Carvalho 380067
@author Philippe Cesar Ramos 380415


 */
package appenginecomm;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author Rodrigo Nascimento de Carvalho 380067
 * @author Philippe Cesar Ramos 380415
 *
 */

//Classe que define um arquivo como um todo, composto por todos os seus elementos, bytearray, id, nome e descricao
//Necessario usar marcacao de XML para que o stub seja gerado corretamente para o cliente. 
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "videoFile")
public class VideoFile{
    @XmlElement(name = "filename")
    protected final String filename;
    @XmlElement(name = "id")
    protected final String id;
    @XmlElement(name = "data")
    protected final byte[] data;
    @XmlElement(name = "description")
    protected final String description;
    
    public VideoFile(){
        this.filename = null;
        this.id = null;
        this.data = null;
        this.description = null;
    }
    
    public VideoFile(String filename, String id, byte[] data, String description){
        this.filename = filename;
        this.id = id;
        this.data = data;
        this.description = description;
    }
    
    public String getFilename(){
        return this.filename;
    }
    
    public String getId(){
        return this.id;
    }
    
    public String getDescription(){
        return this.description;
    }
    
    public byte[] getData(){
        return this.data;
    }
}
