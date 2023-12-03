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
    public ResponseEntity<?> shortenUrl(@RequestParam(name = "url") String url) {
        if (StringUtils.isBlank(url)) {
            return new ResponseEntity<>("Missing URL", HttpStatus.BAD_REQUEST);
        }
        try {
            Map<String, Object> responseMap = new LinkedHashMap<String, Object>();
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
        String lengthyUrl = urlShortenerService.getLengthyUrl(url);
        if(null == lengthyUrl) {
            return new ResponseEntity<>("Service not found!", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(lengthyUrl)).build();
    }
}
