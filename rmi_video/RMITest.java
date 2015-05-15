/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package rmi_video;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;


/**
 * @author Rodrigo Nascimento de Carvalho 380067
 * @author Philippe Cesar Ramos 380415
 * 
 */
public class RMITest {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
                VideoServer server = new VideoServer();
                //Utiliza a porta 50000
                LocateRegistry.createRegistry(50000);
                VideoInterface stub = (VideoInterface) UnicastRemoteObject.exportObject(server, 0);
                
                Registry registry = LocateRegistry.getRegistry(50000);
                //Faz o bind
                registry.bind("VideoInterface", stub);
                
                
                System.err.println("Server started.");
            } catch (Exception ex) {
                System.err.println("Server exception: " + ex.toString());
                ex.printStackTrace();
            }
    }
    
    
    
}
