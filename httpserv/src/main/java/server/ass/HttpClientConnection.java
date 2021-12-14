package server.ass;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.imageio.ImageIO;

public class HttpClientConnection implements Runnable {
    private Socket socket;
    private int id;
    private List<String> dirList;
    //private String clientIn;
    private ArrayList<String> clientIn;
    private String rsName;
    
    public HttpClientConnection(Socket socket, int id, List<String> dirList) {
        this.socket = socket;
        this.id = id;
        this.dirList = dirList;
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
            delim.close();      //read client input
        
        
            rsName=clientIn.get(1);
            if(rsName.equals("/")){rsName= "/index.html";}  //rRENAME TO INDEX
       // while (!"close".equals(line) && null != line) {
         //while (true) {
            //System.out.println("Client " + id + ": " + line);           
            try {
               for(String s: dirList)
               {rsName = s + rsName;   //add directory name to searched file name
                
                if (clientIn.contains("GET")==false) 
                {   System.out.println("NOT A GET METHOD");

                    htpwrite.writeString("HTTP/1.1 405 Method Not Allowed\r\n \r\n " + clientIn.get(0)+" not supported\r\n");
                    htpwrite.flush();
                    htpwrite.close();
                    socket.close();
                } 
                else if (servCmd.chkPthReadable(rsName)==false) 
                {  htpwrite.writeString("HTTP/1.1 405 Method Not Allowed\r\n \r\n" + clientIn.get(1) + "not supported\r\n" ); socket.close();}

                else if (servCmd.chkPthReadable(rsName)==true)
                {
                //Path clientRequest = Paths.get(rsName);
                byte[] bytes = Files.readAllBytes(Paths.get(rsName));
                htpwrite.writeBytes(bytes);
                htpwrite.writeString("HTTP/1.1 200 OK\r\n  \r\n " + rsName + " as bytes");
                socket.close();
                }

                else if (servCmd.chkPthReadable(rsName)==true && rsName.contains(".png")){
                    Path image = Paths.get(rsName);
                    File imageF = image.toFile();
                    BufferedImage bImage = ImageIO.read(imageF);
                   ByteArrayOutputStream bos = new ByteArrayOutputStream();
                   ImageIO.write(bImage,"png",bos);
                   byte [] data = bos.toByteArray();

                    
                    byte[] bytes = Files.readAllBytes(Paths.get(rsName));
                    htpwrite.writeBytes(bytes);
                    htpwrite.writeString("HTTP/1.1 200 OK\r\n Content-Type: image//png \r\n" + rsName + " as bytes");
                    socket.close();  
                }else{System.out.println("input not read");return;}
                
                }  //closing bracket for FOR loop. check every directory described initially in main 
                socket.close();
            } catch (Exception e) {
                e.printStackTrace();
                break;
            } 
        }
    }
}
