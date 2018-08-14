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

    private static HttpServer server;

    static class MainHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            InputStream is = exchange.getRequestBody();
            is.close();

            Headers h = exchange.getResponseHeaders();
            h.set("Content-Type", "text/html");

            File file = new File("src/app/index.html");

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

            File file = new File("src/app/jquery.js");

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

            File file = new File("src/app/testdata.json");

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

            TestManager.prepareToRunTests(testNames);

            exchange.sendResponseHeaders(200, 0);
            OutputStream os = exchange.getResponseBody();
            os.close();

        }
    }

    static class StopHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            InputStream is = exchange.getRequestBody();
            is.close();

            new Thread(TestManager::stopTesting).start();

            exchange.sendResponseHeaders(200, 0);
            OutputStream os = exchange.getResponseBody();
            os.close();

        }
    }

    public static void start() {

        try {
            server = HttpServer.create(new InetSocketAddress(8000),0);

            server.createContext("/", new MainHandler());
            server.createContext("/jquery", new JqueryHandler());
            server.createContext("/update", new UpdateHandler());
            server.createContext("/run", new RunHandler());
            server.createContext("/stop", new StopHandler());
            server.setExecutor(null); // creates a default executor
            server.start();

        } catch (Exception e) {
            System.out.println("Testing server couldn't be started!");
            e.printStackTrace();
        }

    }

    public static void stop() {

        try {
            server.stop(0);
        } catch (Exception e) {
            System.out.println("Testing server couldn't be stopped!");
            e.printStackTrace();
        }

    }

}
