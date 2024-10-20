package com.colutti.advanced_features_http_client.sample;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpTimeoutException;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Component
public class MultipleRequestsWithBeanOfHttpClient {

    private final HttpClient httpClient;

    private static final List<String> genresList = Arrays.asList("action", "horror", "comedy", "adventure");

    public MultipleRequestsWithBeanOfHttpClient(HttpClient httpClient) {
        this.httpClient = httpClient;
    }


    public String sampleRequest(){
        if(generateRandomNumber(1,2) == 1){
            return getRequest();
        }
        return postRequest();
    }

    private String postRequest(){
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://Movies-Verse.proxy-production.allthingsdev.co/api/movies/search-movies-by-query"))
                .timeout(Duration.ofSeconds(10))
                .header("Content-Type", "application/json")
                .header("x-apihub-key", "")
                .header("x-apihub-host", "Movies-Verse.allthingsdev.co")
                .header("x-apihub-endpoint", "06344c37-2a53-4936-be17-34568bdc31ab")
                .POST(HttpRequest.BodyPublishers.ofString("{\"query\":\"Batman\"}"))
                .build();
        try {
            HttpResponse<String> response = this.httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.statusCode());
            return response.body();
        } catch (HttpTimeoutException e) {
            System.err.println("Request timed out: " + e.getMessage());
            return null;
        } catch (IOException e) {
            System.err.println("Erro de I/O: " + e.getMessage());
            return null;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Operation interrupted: " + e.getMessage());
            return null;
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
            return null;
        }
    }

    private String getRequest(){
        String movieGenre = genresList.get(generateRandomNumber(0,3));
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://Movies-Verse.proxy-production.allthingsdev.co/api/movies/get-by-genre?genre="+movieGenre))
                .timeout(Duration.ofSeconds(10))
                .header("Content-Type", "application/json")
                .header("x-apihub-key", "")
                .header("x-apihub-host", "Movies-Verse.allthingsdev.co")
                .header("x-apihub-endpoint", "dae9e3d3-6b6c-4fde-b298-ada2806ae563")
                .GET()
                .build();
        try {
            HttpResponse<String> response = this.httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.statusCode());
            return response.body();
        } catch (HttpTimeoutException e) {
            System.err.println("Request timed out: " + e.getMessage());
            return null;
        } catch (IOException e) {
            System.err.println("Erro de I/O: " + e.getMessage());
            return null;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Operation interrupted: " + e.getMessage());
            return null;
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
            return null;
        }
    }

    private static int generateRandomNumber(int min, int max) {
        if (min > max) {
            throw new IllegalArgumentException("O limite inferior deve ser menor ou igual ao limite superior.");
        }
        Random random = new Random();
        return random.nextInt(max - min + 1) + min;
    }
}
