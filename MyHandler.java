import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;  
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;


public class MyHandler implements HttpHandler{

    private final String baseDirectory;

    public MyHandler(String baseDirectory) {
        this.baseDirectory = baseDirectory;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        
        String requestURI = exchange.getRequestURI().getPath();

        // If the request is for the root path ("/"), serve "index.php"
        if ("/".equals(requestURI)) {


            if (Files.exists(Paths.get("./htdocs/index.html"))) {
                // serve the index.html file.
                requestURI = "/htdocs/index.html";

                String filePath = baseDirectory + requestURI;

                // Check if the requested file exists
                Path file = Paths.get(filePath);
                if (Files.exists(file) && Files.isRegularFile(file)) {
                    // Read the PHP script's content
                    String htmlScript = new String(Files.readAllBytes(file), StandardCharsets.UTF_8);

                    // Set response headers
                    exchange.getResponseHeaders().set("Content-Type", "text/html");

                    // Send the PHP script's content as the HTTP response
                    exchange.sendResponseHeaders(200, htmlScript.length());
                    OutputStream os = exchange.getResponseBody();
                    os.write(htmlScript.getBytes(StandardCharsets.UTF_8));
                    os.flush();            
                    os.close();
                } else {
                    // File not found, return a 404 response
                    String response = "File not found.";
                    exchange.getResponseHeaders().set("Content-Type", "text/plain");
                    exchange.sendResponseHeaders(404, response.length());
                    OutputStream os = exchange.getResponseBody();
                    os.write(response.getBytes(StandardCharsets.UTF_8));
                    os.flush();
                    os.close();
                }
            }
            
            if(Files.exists(Paths.get("./htdocs/index.php"))){
                // serve the index.php file.
                requestURI = "/htdocs/index.php";

                String filePath = baseDirectory + requestURI;

                // Check if the requested file exists
                Path file = Paths.get(filePath);
                if (Files.exists(file) && Files.isRegularFile(file)) {
                    // Read the PHP script's content
                    String phpScript = new String(Files.readAllBytes(file), StandardCharsets.UTF_8);

                    // Set response headers
                    exchange.getResponseHeaders().set("Content-Type", "text/html");

                    // Send the PHP script's content as the HTTP response
                    exchange.sendResponseHeaders(200, phpScript.length());
                    OutputStream os = exchange.getResponseBody();
                    os.write(phpScript.getBytes(StandardCharsets.UTF_8));
                    os.flush();            
                    os.close();
                } else {
                    // File not found, return a 404 response
                    String response = "File not found.";
                    exchange.getResponseHeaders().set("Content-Type", "text/plain");
                    exchange.sendResponseHeaders(404, response.length());
                    OutputStream os = exchange.getResponseBody();
                    os.write(response.getBytes(StandardCharsets.UTF_8));
                    os.flush();
                    os.close();
                }
            }

        }

        // GET Request
        if (exchange.getRequestMethod().equalsIgnoreCase("GET")) {

            String requestPath = exchange.getRequestURI().getPath();
            System.out.println(requestPath);

            OutputStream response = exchange.getResponseBody();

            // System.out.println(params);
            String filename = "./htdocs" + requestPath;
            PhpInterpreter phpInterpreter = new PhpInterpreter(exchange, filename);
            String result = phpInterpreter.interpret();

            try {
                exchange.sendResponseHeaders(200, result.length()); // send the ok code (200) and response length as headers.
                response.write(result.getBytes());  // write the output of the php file to response.
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
            
            try {
                response.flush();   // flush the data through the outputstream.
                response.close();   // and close the file.
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }

        if (exchange.getRequestMethod().equalsIgnoreCase("POST")) {

            String requestPath = exchange.getRequestURI().getPath();
            System.out.println(requestPath);

            OutputStream response = exchange.getResponseBody();

            String filename = "./htdocs" + requestPath;

            PhpInterpreter phpInterpreter = new PhpInterpreter(exchange, filename);
            String result = phpInterpreter.interpret();

            try {
                exchange.sendResponseHeaders(200, result.length()); // send the ok code (200) and response length as headers.
                response.write(result.getBytes());  // write the output of the php file to response.
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
            
            try {
                response.flush();   // flush the data through the outputstream.
                response.close();   // and close the file.
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }

        }

    }
 
}

