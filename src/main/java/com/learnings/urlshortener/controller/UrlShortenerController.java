package com.learnings.urlshortener.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UrlShortenerController {

    @GetMapping("/test")
    public String testApi() {
        return "Working..!";
    }
}
