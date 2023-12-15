package com.booking.svc.resources;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class HomeResources {

    @GetMapping
    public String index() {
        return "Booking service is up and running.....";
    }

}
