package com.colutti.advanced_features_http_client.sample;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class JvmArgumentsHttpClient {

    public static void main(String[] args) throws IOException, InterruptedException {

        /*
            The number of seconds to keep idle HTTP connections alive in the keep alive cache.
            This property applies to both HTTP/1.1 and HTTP/2.

            The goal is to optimize resource usage and improve performance,
            avoiding the overhead of repeatedly establishing new connections,
            but also ensuring that connections are not left open indefinitely.
        */
        System.setProperty("jdk.httpclient.keepalive.timeout", "5");

        /*
            The number of seconds to keep idle HTTP/2 connections alive.
            If not set, then the jdk.httpclient.keepalive.timeout setting is used.
        */
        System.setProperty("jdk.httpclient.keepalive.timeout.h2", "5");

        /*
            Enables high-level logging of various events through the Platform Logging API. The value contains a comma-separated list of any of the following items:
            all
            errors
            requests
            headers
            content
            frames
            ssl
            trace
            channel
        */
        System.setProperty("jdk.httpclient.HttpClient.log", "all");

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
