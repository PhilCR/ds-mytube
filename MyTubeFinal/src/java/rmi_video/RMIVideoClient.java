/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.

 */

package rmi_video;

import java.io.IOException;;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;



/**
 *
 * @author Rodrigo Nascimento de Carvalho 380067
 * @author Philippe Cesar Ramos 380415
 * 
 * Classe que representa o cliente do video RMI
 */
public class RMIVideoClient {

    /**
     * @param args the command line arguments
     */
    
    private RMIVideoClient(){ }
    
    //metodo que da upload, recebe o host em que o rmi se encontra, os dados, nome do arquivo, e a sua id 
    public static boolean upload(String host, byte[] data, String filename, String id) {
        //criamos objeto do tipo UploadVideo
        UploadVideo uV = new UploadVideo(host, data, filename, id);
        //mandamos o mesmo rodar e retornar se o arquivo foi upado com sucesso ou nao
        return uV.run();
    }
    
    //metodo que da download, recebe o host e apenas o id para procurar o video
    public static VideoData download(String host, String id){
        //criamos objeto do tipo DownloadVideo que recebe host e id
        DownloadVideo dV = new DownloadVideo(host, id);
        //metodo run de dV retorna um objeto do tipo VideoData, que representa dados de um dado video.
        return dV.run();
    }
    
    //Classe estatica UploadVideo =)
    public static class UploadVideo{
        
        private final String host;
        private final byte[] data;
        private final String filename;
        private final String id;
        
        public UploadVideo(String host, byte[] data, String filename, String id){
            this.host = host;
            this.data = data;
            this.filename = filename;
            this.id = id;
        }
        
        //importante comentar o metodo run desta classe
        public boolean run() {
            try {
                //localiza o registro no endereco do servidor na porta 50000
                Registry registry = LocateRegistry.getRegistry(host, 50000);
                //procura pelo stub da interface de video
                VideoInterface stub = (VideoInterface) registry.lookup("VideoInterface");
                
                //cria o objeto que passamos para o RMIServer, do tipo VideoData
                VideoData d = new VideoData(data, filename, id);
                
                //chamamos entao o metodo addVideo da interface de video, que retorna flag dizendo se foi adicionado com sucesso ou nao
                boolean insertedFlag = stub.addVideo(d);
                return insertedFlag;  
            } catch (NotBoundException | IOException e) {
                System.out.println(e);
                return false;
            }
        }
    }

    //classe de DownloadVideo =)
    public static class DownloadVideo{
        private final String host;
        private final String id;
        
        public DownloadVideo(String host, String id){
            this.host = host;
            this.id = id;
        }
        
        //metodo run retorna um video baixado
        public VideoData run() {
            try {
                //localiza o registro na porta 50000 com endereco que vem em host
                Registry registry = LocateRegistry.getRegistry(host, 50000);
                //procura pela inteerface de video
                VideoInterface stub = (VideoInterface) registry.lookup("VideoInterface");
                
                //chama o metodo getvideo do stub
                VideoData d = stub.getVideo(id);

                //e retorna o que foi achado no servidor, se nada for achado, retornara null
                return d;
            } catch (NotBoundException | IOException e) {
                System.err.println("Erro: " + e);
                return null;
            }
        }
    }
}
