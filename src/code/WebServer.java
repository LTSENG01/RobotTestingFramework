package code;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import testable.TestManager;

import java.io.*;
import java.net.InetSocketAddress;

/**
 * Note: HTTP servers should NOT (debatable) be used on FRC robots. Use NetworkTables on the robot and client laptop.
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

        /*

        TODO: PROBLEM

        When testdata.json changes, this function still serves the original file contents.
        Somehow I need to change the file handler after each edit to the file or restart the WebServer.

         */


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

    static class RunHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            InputStream is = exchange.getRequestBody();
            String testNames = new String(is.readAllBytes());
            is.close();

            System.out.println(testNames);

            TestManager.prepareToRunTests(testNames);

            exchange.sendResponseHeaders(200, 0);
            OutputStream os = exchange.getResponseBody();
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
            server.createContext("/run", new RunHandler());
            server.setExecutor(null); // creates a default executor
            server.start();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
