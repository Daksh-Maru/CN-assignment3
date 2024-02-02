import java.io.*;
import java.net.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SimpleHttpServer {
    private static final int PORT = 8080;

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Server listening on port " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                handleClient(clientSocket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleClient(Socket clientSocket) {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            String requestLine = in.readLine();
            String[] requestTokens = requestLine.split(" ");
            String method = requestTokens[0];
            String path = requestTokens[1];

            // Minimal response headers
            out.println("HTTP/1.1 200 OK");
            out.println("Date: " + new Date());
            out.println("Server: SimpleHTTPServer");
            out.println("Content-Type: text/html; charset=utf-8");

            // Handling different HTTP methods
            if (method.equals("GET") || method.equals("HEAD")) {
                handleGet(out, path);
            } else if (method.equals("POST")) {
                handlePost(out);
            } else if (method.equals("PUT")) {
                handlePut(out);
            } else if (method.equals("DELETE")) {
                handleDelete(out);
            } else {
                out.println("HTTP/1.1 405 Method Not Allowed");
                out.println();
            }

            // Set and send cookies
            Map<String, String> cookies = new HashMap<>();
            cookies.put("user", "John Doe");
            out.println("Set-Cookie: " + buildCookieHeader(cookies));

            out.close();
            in.close();
            clientSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleGet(PrintWriter out, String path) {
        // Handle different response codes and paths
        if (path.equals("/")) {
            out.println("HTTP/1.1 200 OK");
            out.println();
            out.println("<html><body><h1>Hello, World!</h1></body></html>");
        } else if (path.equals("/error")) {
            out.println("HTTP/1.1 500 Internal Server Error");
            out.println();
            out.println("<html><body><h1>500 Internal Server Error</h1></body></html>");
        } else if (path.equals("/notfound")) {
            out.println("HTTP/1.1 404 Not Found");
            out.println();
            out.println("<html><body><h1>404 Not Found</h1></body></html>");
        } else {
            out.println("HTTP/1.1 200 OK");
            out.println();
            out.println("<html><body><h1>GET request on " + path + "</h1></body></html>");
        }
    }

    private static void handlePost(PrintWriter out) {
        // Handle POST method
        out.println("HTTP/1.1 200 OK");
        out.println();
        out.println("<html><body><h1>POST request handled</h1></body></html>");
    }

    private static void handlePut(PrintWriter out) {
        // Handle PUT method
        out.println("HTTP/1.1 200 OK");
        out.println();
        out.println("<html><body><h1>PUT request handled</h1></body></html>");
    }

    private static void handleDelete(PrintWriter out) {
        // Handle DELETE method
        out.println("HTTP/1.1 200 OK");
        out.println();
        out.println("<html><body><h1>DELETE request handled</h1></body></html>");
    }

    private static String buildCookieHeader(Map<String, String> cookies) {
        StringBuilder header = new StringBuilder();
        for (Map.Entry<String, String> entry : cookies.entrySet()) {
            header.append(entry.getKey()).append("=").append(entry.getValue()).append("; ");
        }
        return header.toString();
    }
}
