package com.colutti.advanced_features_http_client.sample;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpTimeoutException;
import java.time.Duration;

public class HttpClientWorkingWithIOException {

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8085), 0);
        server.createContext("/io-exception", new HttpClientWorkingWithIOException.IOExceptionHandler());
        server.setExecutor(null);
        server.start();

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8085/io-exception"))
                .timeout(Duration.ofSeconds(9)) // Timeout of 9 seconds
                .GET()
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("Response: " + response.body());
        } catch (HttpTimeoutException e) {
            System.err.println("Request timed out: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("I/O Error: " + e.getMessage());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Operation interrupted: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
        } finally {
            server.stop(0); // Stop server after test
        }
    }

    static class IOExceptionHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            // Simula uma IOException ao tentar escrever a resposta
            throw new IOException("Simulate of IOException");
        }
    }
}
