package com.learnings.urlshortener.controller;

import com.learnings.urlshortener.services.UrlShortenerService;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;
import java.net.URI;

@RestController
public class UrlShortenerController {

    @Autowired
    private UrlShortenerService urlShortenerService;

    @GetMapping("/test")
    public String testApi() {
        return "Working..!";
    }

    @PostMapping("/shortenUrl")
    public ResponseEntity<?> shortenUrl(@RequestBody String url) {
        Map<String, Object> responseMap = new LinkedHashMap<String, Object>();

        if (StringUtils.isBlank(url)) {
            responseMap.put("error", "Missing field: url");
            return new ResponseEntity<>(responseMap, HttpStatus.BAD_REQUEST);
        }
        try {
            String key = urlShortenerService.shortenUrl(url);
            responseMap.put("key", key);
            responseMap.put("long_url", url);
            responseMap.put("short_url", urlShortenerService.getApiUrl() + key);
            return new ResponseEntity<>(responseMap, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{url}")
    public ResponseEntity<?> redirectShortenedUrl(@PathVariable("url") String url) {
        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create("https://www.google.com/")).build();
    }
}
