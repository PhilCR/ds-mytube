/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytube;

import appenginecomm.Manip;
import appenginecomm.VideoFile;
import java.io.UnsupportedEncodingException;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import rmi_video.RMIVideoClient;
import rmi_video.VideoData;

/**
 *
 * @author Rodrigo Nascimento de Carvalho 380067
 * @author Philippe Cesar Ramos 380415
 */
@WebService(serviceName = "MyTubeWS")
public class MyTubeWS {

    /**
     * This is a sample web service operation
     * @param txt
     * @return 
     */
    @WebMethod(operationName = "hello")
    public String hello(@WebParam(name = "name") String txt) {
        return "Hello " + txt + " !";
    }

    /**
     * Web service operation
     * @param filename
     * @param filedata
     * @param filedescription
     * @return 
     */
    @WebMethod(operationName = "uploadVideo")
    public String uploadVideo(@WebParam(name = "filename") String filename, @WebParam(name = "filedata") byte[] filedata, @WebParam(name = "filedescription") String filedescription) {
        //configuracao do host do RMI, dependendo de onde o servico estiver rodando, mudar o ip =0
        String RMI_HOST = "192.168.105.119";
        
        //cria novo objeto de manipulacao de app engine
        Manip manipulation = new Manip();
        //gera um id 
        String id = manipulation.generateId();
        
        //se o id for nulo, retorna erro, caso contrario continua
        if(id != null){
            //chamada do metodo de upload do cliente RMI
            boolean uploadRMI = RMIVideoClient.upload(RMI_HOST, filedata, filename, id);
            //se uploadRMI eh true, entao o upload foi realizado com sucesso, continuar.
            if(uploadRMI){
                try {
                    //por fim da upload da descricao e retorna o id do arquivo
                    boolean uploadDesc = manipulation.sendFile(filedescription, id);
                    if(uploadDesc){
                        return id;
                    }else{
                        return "Erro: Não foi possível armazenar a descrição do vídeo.";
                    }
                } catch (UnsupportedEncodingException ex) {
                    return "Erro: "+ex.getLocalizedMessage();
                }
            }else{
                return "Erro: Nâo foi possível se comunicar com o servidor RMI no momento.";
            }
        }else{
            return "Erro: Não foi possível gerar um id para este vídeo.";
        }
    }

    /**
     * Web service operation
     * @param id
     * @return 
     */
    @WebMethod(operationName = "downloadVideo")
    public VideoFile downloadVideo(@WebParam(name = "id") String id) {
        //tambem necessita do endereco do servidor rmi
        String RMI_HOST = "192.168.105.119";
        
        //cria novo objeto de manipulacao local
        Manip manipulation = new Manip();
        try {
            //tenta dar fetch da descricao
            String description = manipulation.receiveDescription(id);
            //se consegue, entao prossegue e tenta pegar os dados do video
            if(description != null){
                VideoData d = RMIVideoClient.download(RMI_HOST, id);
                //se consegue pegar ambos, entao retorne um objeto de arquivo de video =)
                if(d != null){
                    return new VideoFile(d.getFileName(), d.getId(), d.getData(), description);
                }else{
                    return null;
                }
            }else{
                return null;
            }
        } catch (UnsupportedEncodingException ex) {
            return null;
        }
    }
}
