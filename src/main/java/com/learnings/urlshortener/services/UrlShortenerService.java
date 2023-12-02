package com.learnings.urlshortener.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
public class UrlShortenerService {

    @Autowired
    private Environment env;

    public String shortenUrl(String lengthyUrl) {
        return buildEncryptedTinyKey(lengthyUrl);
    }

    private String buildEncryptedTinyKey(String lengthyUrl) {
        Integer uniqueSum = 0;
        for (int i = 0; i < lengthyUrl.length(); i++) {
            uniqueSum += (int) lengthyUrl.charAt(i) * i;
        }
        return Base64.getEncoder().encodeToString(uniqueSum.toString().getBytes(StandardCharsets.UTF_8));
    }

    public String getApiUrl() {
        return env.getProperty("url-shortener.api.url");
    }
}
