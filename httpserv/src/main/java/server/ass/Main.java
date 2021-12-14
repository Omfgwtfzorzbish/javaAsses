package server.ass;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    
    public static void main(String[] args) {
        Socket socket;
        ServerSocket serverSocket;
        Integer port = 0;
        String dirIn="";
        List<String> inputA = new ArrayList<String>(3);
        List<String> direc = new ArrayList<String>();
        
        String delimRslt="";
        
        if (args != null && args.length >= 1)
        {
            for(String s:args)
            {
            inputA.add(s);
            }    
            //for(String z:inputA){System.out.println(z);}         
                if(inputA.contains("--port" )==true && inputA.contains("--docRoot")==false)
                {//System.out.println(inList.get(inList.indexOf(s)+1));
                    port=Integer.valueOf(inputA.get(1));
                    direc.add("/target");
                }
                else if(inputA.contains("--docRoot")== true && inputA.contains("--port")==false){
                    dirIn = inputA.get(1);
                    Scanner delim = new Scanner (dirIn);
                    delim.useDelimiter(":");
                    while(delim.hasNext()){
                        String dirIn2=delim.next();
                        direc.add(dirIn2);}
                        delim.close();
                        }

                else if (inputA.contains("--port") ==true && inputA.contains("--docRoot")==true){
                        port = Integer.valueOf(inputA.get(1));
                        dirIn = inputA.get(3);
                        //System.out.println(inputA);
                        Scanner delim = new Scanner (dirIn);
                        delim.useDelimiter(":");
                        while(delim.hasNext()){
                            String dirIn2=delim.next();
                            direc.add(dirIn2);}
                            delim.close();
                        } 
                else{port=3000;direc.add("/target");}
                
               // System.out.println("port: " +port); 
                System.out.println("Directory List: "); 
               for(String s:direc){System.out.println(s);}
        }
        
        /*
        ExecutorService threadPool = Executors.newFixedThreadPool(3);
        serverSocket = new ServerSocket(port);
        System.out.println("Server listening at port " + port + "...");

        try 
        {
            while(true){
                socket = serverSocket.accept();
                int id = (int) (Math.random()*100);
                HttpClientConnection worker = new HttpClientConnection(socket, id, inputFile);
                threadPool.submit(worker);
            }

        } finally{serverSocket.close();}
        */
       

    }
    
}
