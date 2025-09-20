package io.vertigo.shell.server;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class WebServer {

    public static void main(String[] args) throws IOException {
        int port = 8081; // Using port 8081 to avoid conflict
        String rootDir = "src/main/java/io/vertigo/shell/server/";
        
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        System.out.println("Web server started on port " + port);
        System.out.println("Serving files from: " + new File(rootDir).getAbsolutePath());

        server.createContext("/", new FileHandler(rootDir));
        server.setExecutor(null);
        server.start();
    }

    static class FileHandler implements HttpHandler {
        private final String rootDir;

        public FileHandler(String rootDir) {
            this.rootDir = rootDir;
        }

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String requestedPath = exchange.getRequestURI().getPath();
            
            if (requestedPath.equals("/")) {
                requestedPath = "/index.html";
            }

            Path filePath = Paths.get(rootDir, requestedPath.substring(1)).toAbsolutePath();
            File file = filePath.toFile();

            if (file.exists() && !file.isDirectory()) {
                String contentType = "application/octet-stream";
                if (requestedPath.endsWith(".html")) {
                    contentType = "text/html; charset=utf-8";
                    // Add Permissions-Policy header for HTML files
                    exchange.getResponseHeaders().set("Permissions-Policy", "encrypted-media=*");
                } else if (requestedPath.endsWith(".css")) {
                    contentType = "text/css; charset=utf-8";
                } else if (requestedPath.endsWith(".ts")) {
                    contentType = "application/javascript; charset=utf-8";
                } else if (requestedPath.endsWith(".js")) {
                    contentType = "application/javascript; charset=utf-8";
                }

                exchange.getResponseHeaders().set("Content-Type", contentType);
                exchange.sendResponseHeaders(200, file.length());
                try (OutputStream os = exchange.getResponseBody()) {
                    Files.copy(filePath, os);
                }
            } else {
                String response = "404 (Not Found): " + requestedPath;
                exchange.sendResponseHeaders(404, response.length());
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(response.getBytes());
                }
            }
        }
    }
}
