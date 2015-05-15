/*
@author Rodrigo Nascimento de Carvalho 380067
@author Philippe Cesar Ramos 380415

*/

package appenginecomm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Manip {
        //metodo que envia descricao do arquivo ao appengine,
        //recebe o a descricao do arquivo em questao e tambem o id gerado previamente
	public boolean sendFile(String description, String id) throws UnsupportedEncodingException{
		try{
                        //url com a chamada que sera feita ao app no appengine
			URL url = new URL("http://1-dot-otherspacing.appspot.com/storevideo");
                        
                        //cria conexao com a url em questao
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			
			connection.setRequestMethod("POST");
			//escreve no body da request, tanto o id quanto o conteudo, no caso a descricao
                        try (OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream())) {
                            writer.write("id="+id+"&content="+description);
                        }
			
                        //se a conexao tiver codigo 200, entao retorna true, caso contrario false
			if(connection.getResponseCode() == HttpURLConnection.HTTP_OK){
                            return true;
			}else{
                            return false;
			}
		}catch(MalformedURLException e){
                    return false;	
		}catch(IOException e){
                    return false;
		}
	}
	
        //recebe a descricao armazenada no google appengine
	public String receiveDescription(String id) throws UnsupportedEncodingException{
		try{
                        //url do metodo get da cloud
			URL url = new URL("http://1-dot-otherspacing.appspot.com/fetch?id="+id);
			
                        //cria a conexao com a url em questao
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                        
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setRequestMethod("GET");
			
			String description;
			
			//se o codigo for ok do retorno, caso contrario retorne null, isso tambem vale para as excecoes
			if(connection.getResponseCode() == HttpURLConnection.HTTP_OK){
                            //entao temos que ler o que recebemos no input stream 
                                BufferedReader in = new BufferedReader(
                                    new InputStreamReader(connection.getInputStream()));
                                String inputLine;
                                StringBuilder response = new StringBuilder();
                                
                                //le o que foi retornado e vai colocando na resposta
                                while ((inputLine = in.readLine()) != null) {
                                    response.append(inputLine);
                                }
                                in.close();
                                //retorna a descricao
                                description = response.toString();
                                return description;
			}else{
				System.out.println("fail");
                                return null;
			}
			
		}catch(MalformedURLException e){
			e.printStackTrace();
                        return null;
		}catch(IOException e){
			e.printStackTrace();
                        return null;
		}catch(Exception e){
			e.printStackTrace();
                        return null;
		}
	}
        
        //gera a id no appengine
        public String generateId(){
            try{
                        //mesma coisa dos codigos anteriores, chama geraid, que eh um metodo get
			URL url = new URL("http://1-dot-otherspacing.appspot.com/geraid");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                        connection.setDoInput(true);
			connection.setDoOutput(true);
			
			connection.setRequestMethod("GET");
			
			//se recebe gerado com sucesso, entao pega a id gerada, coloca numa resposta no stringbuilder e retorna =), para erros retorna-se null
			if(connection.getResponseCode() == HttpURLConnection.HTTP_OK){
                                BufferedReader in = new BufferedReader(
                                    new InputStreamReader(connection.getInputStream()));
                                String inputLine;
                                StringBuilder response = new StringBuilder();

                                while ((inputLine = in.readLine()) != null) {
                                    response.append(inputLine);
                                }
                                in.close();
				return response.toString();
			}else{
				return null;
			}
		}catch(MalformedURLException e){
			return null;
		}catch(IOException e){
			return null;
		}
        }
}

