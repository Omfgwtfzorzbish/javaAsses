package server.ass;

public class HttpClientConnection implements Runnable {
    private final Socket socket;
    private int id;
    private String inputFile;
}
