package server.ass;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class HttpServer {
    
    public boolean chkPthExt(String s)  //chk is true
    {Path path = Paths.get(s);
    if(Files.exists(path)){ return true;}else{return false;}
    }

    public boolean chkIsDir(String s)  //chk is true
    {Path path = Paths.get(s);
    if(Files.isDirectory(path)){ return true;}else{return false;}
    }

    public boolean chkPthReadable(String s)      
    {Path path = Paths.get(s); 
    File file = path.toFile(); 
    try{
        if(file.canRead()==true){ return true;}else{return false;}} catch(Exception e){System.err.println("problem with chk path readable");return false;}
    }
}
