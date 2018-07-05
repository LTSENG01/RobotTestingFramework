package code;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.*;
import java.net.InetSocketAddress;

/**
 * Note: HTTP servers should NOT be used on FRC robots. Use NetworkTables on the robot and client laptop.
 */

public class WebServer {

    static class MainHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            InputStream is = exchange.getRequestBody();
            is.close();

            Headers h = exchange.getResponseHeaders();
            h.set("Content-Type", "text/html");

            File file = new File(
                    getClass()
                            .getResource("/app/index.html")
                            .getFile())
                    .getCanonicalFile();

            exchange.sendResponseHeaders(200, file.length());

            OutputStream os = exchange.getResponseBody();
            FileInputStream fs = new FileInputStream(file);
            os.write(fs.readAllBytes());
            fs.close();
            os.close();

        }
    }

    static class JqueryHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            InputStream is = exchange.getRequestBody();
            is.close();

            Headers h = exchange.getResponseHeaders();
            h.set("Content-Type", "text/javascript");

            File file = new File(
                    getClass()
                            .getResource("/app/jquery.js")
                            .getFile())
                    .getCanonicalFile();

            exchange.sendResponseHeaders(200, file.length());

            OutputStream os = exchange.getResponseBody();
            FileInputStream fs = new FileInputStream(file);
            os.write(fs.readAllBytes());
            fs.close();
            os.close();

        }
    }

    static class UpdateHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            InputStream is = exchange.getRequestBody();
            is.close();

            Headers h = exchange.getResponseHeaders();
            h.set("Content-Type", "application/json");

            File file = new File(
                    getClass()
                            .getResource("/app/testdata.json")
                            .getFile())
                    .getCanonicalFile();

            exchange.sendResponseHeaders(200, file.length());

            OutputStream os = exchange.getResponseBody();
            FileInputStream fs = new FileInputStream(file);
            os.write(fs.readAllBytes());
            fs.close();
            os.close();

        }
    }

    public static void start() {

        HttpServer server;

        try {
            server = HttpServer.create(new InetSocketAddress(8000),0);

            server.createContext("/", new MainHandler());
            server.createContext("/jquery", new JqueryHandler());
            server.createContext("/update", new UpdateHandler());
            server.setExecutor(null); // creates a default executor
            server.start();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
