package com.colutti.advanced_features_http_client.sample;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLParameters;
import java.io.IOException;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class HttpClientBuilderWithAllMethods {

    public static void main(String[] args) throws NoSuchAlgorithmException, IOException, InterruptedException {
        /*
           Authenticator, this method allow you to set credentials of your HttpCLient (like a user and password)
        */
        Authenticator authenticator = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("user", "password".toCharArray());
            }
        };

        /*
            Allows you to define a CookieHandler, which manages cookies.
            This is useful for handling sessions and authentication that rely on cookies.
        */
        CookieHandler cookieHandler = new CookieManager();

        /*
            Defines a custom executor to run asynchronous tasks.
            This allows you to control how HTTP requests are executed in parallel.
        */
        Executor executor = Executors.newFixedThreadPool(4);

        /*
            Defines an SSL context that specifies how secure (HTTPS)
            connections should be configured, including certificate management and security protocols.
        */
        SSLContext sslContext = SSLContext.getDefault();

        /*
            Defines additional security parameters for SSL connections,
            such as which encryption algorithms to use and which certificates to accept.
        */
        SSLParameters sslParameters = new SSLParameters();

        HttpClient client = HttpClient.newBuilder()
                .authenticator(authenticator)
                //Sets the connect timeout duration for this client.
                .connectTimeout(Duration.ofSeconds(10))
                .cookieHandler(cookieHandler)
                .executor(executor)
                /*
                    Specifies how the client should handle HTTP redirects.
                    You can choose to automatically follow redirects or not,
                    depending on the policy you choose (e.g. ALWAYS, NEVER, or NORMAL).
                */
                .followRedirects(HttpClient.Redirect.ALWAYS)
                .priority(1) // priority to HTTP/2
                /*
                    Allows you to define a ProxySelector,
                    which determines how the client should connect to external networks, using a proxy server if necessary.
                */
                .proxy(ProxySelector.getDefault())
                .sslContext(sslContext)
                .sslParameters(sslParameters)
                .version(HttpClient.Version.HTTP_2)
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
