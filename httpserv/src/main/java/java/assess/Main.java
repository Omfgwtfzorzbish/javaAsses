package java.assess;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    
    public static void main(String[] args) {
        Socket socket;
        ServerSocket serverSocket;
        boolean inputChk = true;
        Integer port = 0;
        String direcd = "";
        String docR = "./static";
        String dirIn="";
        List<String> inputA = new ArrayList<String>();
        List<String> direc = new ArrayList<String>();
        Scanner delim = new Scanner (dirIn);
        if (args != null && args.length >= 1)
        {
            for(String s:args)
            {
            //System.out.println(s);
            inputA.add(s);
            }
            for(String s:inputA)
                {
                if(s.equals("--port" )&& inputA.contains("--docRoot")==false)
                {//System.out.println(inList.get(inList.indexOf(s)+1));
                    port=Integer.valueOf(inputA.get(inputA.indexOf(s)+1));
                    direc.add("target");
                }
                else if(s.equals("--docRoot")&& inputA.contains("--port")==false){
                    dirIn = inputA.get(1);
                    delim.useDelimiter(":");
                    while(delim.hasNext()){
                        direc.add(delim.next());}
                        }

                else if (inputA.contains("--port") && inputA.contains("--docRoot")){
                        port = Integer.valueOf(inputA.get(1));
                        dirIn = inputA.get(3);
                    delim.useDelimiter(":");
                    while(delim.hasNext()){
                        direc.add(delim.next());}
                        } 
                else{port=3000;direc.add("/target");}
                }
                System.out.println("port: " +port); 
                System.out.println("Directory List: "); 
                for(String s:direc){System.out.println(s);}
        }
    }
    
}
