package ru.hogwarts.schhogwarts.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Profile("!production")
@RequestMapping("/info")
public class InfoControllerTest {
    @Value("${server.port:8081}")
    private String port;
    @GetMapping("/port")
    public String getPort(){
        return port;
    }
}
