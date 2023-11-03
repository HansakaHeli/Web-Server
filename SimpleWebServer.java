import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;


public class SimpleWebServer {
    public static void main(String[] args) throws IOException {

        // Create an HTTP server that listens on port 2728
        HttpServer server = HttpServer.create(new InetSocketAddress(2728), 0);

        // Create a context for the root path ("/") and set a handler
        server.createContext("/", new MyHandler("."));

        // Start the server
        server.start();

        System.out.println("Server staared on port 2728");
    }
}