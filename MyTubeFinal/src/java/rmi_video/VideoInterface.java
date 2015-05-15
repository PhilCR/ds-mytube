/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package rmi_video;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author Rodrigo Nascimento de Carvalho 380067
 * @author Philippe Cesar Ramos 380415
 */
public interface VideoInterface extends Remote{
    boolean addVideo(VideoData d) throws RemoteException;
    VideoData getVideo(String id) throws RemoteException;
}
