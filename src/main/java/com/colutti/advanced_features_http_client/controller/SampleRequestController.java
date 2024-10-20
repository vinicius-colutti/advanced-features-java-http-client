package com.colutti.advanced_features_http_client.controller;

import com.colutti.advanced_features_http_client.sample.MultipleRequestsWithBeanOfHttpClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sample-request")
public class SampleRequestController {

    private final MultipleRequestsWithBeanOfHttpClient multipleRequestsWithBeanOfHttpClient;

    public SampleRequestController(MultipleRequestsWithBeanOfHttpClient multipleRequestsWithBeanOfHttpClient) {
        this.multipleRequestsWithBeanOfHttpClient = multipleRequestsWithBeanOfHttpClient;
    }

    @GetMapping
    public String sampleRequest(){
        return this.multipleRequestsWithBeanOfHttpClient.sampleRequest();
    }
}
