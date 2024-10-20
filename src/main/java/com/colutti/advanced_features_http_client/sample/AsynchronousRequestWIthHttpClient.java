package com.colutti.advanced_features_http_client.sample;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class AsynchronousRequestWIthHttpClient {

    public static void main(String[] args) throws InterruptedException {
        HttpClient client = HttpClient.newBuilder()
                .build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://Movies-Verse.proxy-production.allthingsdev.co/api/movies/search-movies-by-query"))
                .timeout(Duration.ofMinutes(2))
                .header("Content-Type", "application/json")
                .header("x-apihub-key", "")
                .header("x-apihub-host", "Movies-Verse.allthingsdev.co")
                .header("x-apihub-endpoint", "06344c37-2a53-4936-be17-34568bdc31ab")
                .POST(HttpRequest.BodyPublishers.ofString("{\"query\":\"Batman\"}"))
                .build();
        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(response -> { System.out.println(response.statusCode());
                    return response; } )
                .thenApply(HttpResponse::body)
                .thenAccept(System.out::println);

        //Sleep to wait for the asynchronous response (this is not recommended in production code, only for testing)
        Thread.sleep(5000);
    }
}
