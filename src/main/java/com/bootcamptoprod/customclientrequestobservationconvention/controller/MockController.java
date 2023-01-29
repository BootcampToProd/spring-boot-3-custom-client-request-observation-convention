package com.bootcamptoprod.customclientrequestobservationconvention.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * The type Mock controller.
 * This class will act as a mock controller for external service. This will mimic the behaviour of external service.
 */
@RestController
public class MockController {

    @GetMapping("/user")
    public String userDetails() {
        return "user details";
    }
}
