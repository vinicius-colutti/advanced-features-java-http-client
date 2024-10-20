package com.colutti.advanced_features_http_client.sample;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class SpecifiesHttpClientVersion {


    /*
        The Java HTTP Client supports both HTTP/1.1 and HTTP/2. By default the client
        will send requests using HTTP/2. Requests sent to servers that do not yet support HTTP/2
        will automatically be downgraded to HTTP/1.1.

        reference: https://openjdk.org/groups/net/httpclient/intro.html#:~:text=HttpResponse.BodyHandlers%3A%3AfromLineSubscriber(...)-,HTTP/2,-The%20Java%20HTTP
    */
    public static void main(String[] args) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
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
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.statusCode());
        System.out.println(response.body());
    }
}
