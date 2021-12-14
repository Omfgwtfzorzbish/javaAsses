package server.ass;

import java.net.Socket;
import java.util.ArrayList;

public class HttpClientConnection implements Runnable {
    private Socket socket;
    private int id;
    private ArrayList<String> inputFile;

    public HttpClientConnection(Socket socket, int id, ArrayList<String> inputFile) {
        this.socket = socket;
        this.id = id;
        this.inputFile = inputFile;
    }
    @Override
    public void run() {
        PrintWriter out = null;
        BufferedReader in = null;
        String line = "";
        System.out.println("Connection ID: " + id);

        try {
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
            line = in.readLine();
        } catch (IOException ioe) {
            System.out.println("Something went wrong..");
        }

        while (!"close".equals(line) && null != line) {

            System.out.println("Client " + id + ": " + line);
            
            try {
                
                if ("get-cookie".equals(line)) {
                    System.out.println("Sending a cookie to client " + id);
                    out.println("cookie-text " + 
                            new Cookie().getCookie(inputFile));
                    out.flush();
                    line = in.readLine();
                } else {
                    out.println("Server: you said " + line);
                    out.flush();
                    line = in.readLine();
                }

            } catch (Exception e) {
                e.printStackTrace();
                break;
            } 
        }
    }
}
