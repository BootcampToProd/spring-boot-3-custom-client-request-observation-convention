package com.bootcamptoprod.customclientrequestobservationconvention.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class CustomClientRequestObservationConventionController {

    @Autowired
    private RestTemplate restTemplate;

    private final String userServiceHost = "http://localhost:8080";

    @GetMapping("/hello")
    public String hello() {
        String message = "hello";

        ResponseEntity<String> response
                = restTemplate.getForEntity(userServiceHost + "/user?id=1", String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            message = message + ". " + response.getBody();
        }

        return message;
    }
}
