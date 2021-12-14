package server.ass;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class HttpClientConnection implements Runnable {
    private Socket socket;
    private int id;
    private ArrayList<String> inputFile;
    //private String clientIn;
    private ArrayList<String> clientIn;
    private String rsName;
    
    public HttpClientConnection(Socket socket, int id, ArrayList<String> inputFile) {
        this.socket = socket;
        this.id = id;
        this.inputFile = inputFile;
    }
    @Override
    public void run() {
        HttpServer servCmd = new HttpServer();
        HttpWriter htpwrite = new HttpWriter(socket.getOutputStream());
        BufferedReader in = null;
        String line = "";
        System.out.println("Connection ID: " + id);

        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            line = in.readLine();
        } catch (IOException ioe) {
            System.out.println("Something went wrong..");
        }

        Scanner delim = new Scanner (line);
        delim.useDelimiter(" ");
        while(delim.hasNext()){
            String dirIn2=delim.next();
            clientIn.add(dirIn2);}
            delim.close();
            
       // while (!"close".equals(line) && null != line) {
         //while (true) {
            //System.out.println("Client " + id + ": " + line);           
            try {
                
                if (clientIn.contains("GET")==false) 
                {   System.out.println("NOT A GET METHOD");

                    htpwrite.writeString("HTTP/1.1 405 Method Not Allowed\r\n \r\n " + clientIn.get(0)+" not supported\r\n");
                    htpwrite.flush();
                    htpwrite.close();
                    socket.close();
                } 
                else if (line.contains("GET")==true) 
                {  rsName=clientIn.get(1);
                        if(rsName.equals("//")){rsName= "//index.html";}
                            if(servCmd.chkPthReadable(clientIn.get(1))==true){  
                                Path request = Paths.get(clientIn.get(1));
                            }else
                            {htpwrite.writeString("HTTP/1.1 405 Method Not Allowed\r\n \r\n" + clientIn.get(1) + "not supported\r\n" ); socket.close();}
                        
                }

            } catch (Exception e) {
                e.printStackTrace();
                break;
            } 
        }
    }
}
