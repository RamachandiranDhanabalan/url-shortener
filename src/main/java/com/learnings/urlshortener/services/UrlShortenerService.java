package com.learnings.urlshortener.services;

import com.learnings.urlshortener.dao.UrlShortenerMapDao;
import com.learnings.urlshortener.model.UrlShortenerMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
public class UrlShortenerService {

    @Autowired
    private Environment env;

    @Autowired
    private UrlShortenerMapDao urlShortenerMapDao;

    public String shortenUrl(String lengthyUrl) {
        String tinyUrlKey = buildEncryptedTinyKey(lengthyUrl);
        while (null != urlShortenerMapDao.getOneUrlShortenerMap(tinyUrlKey)) {
            tinyUrlKey = buildEncryptedTinyKey(lengthyUrl);
        }
        UrlShortenerMap mapEntry = new UrlShortenerMap(lengthyUrl, tinyUrlKey);
        urlShortenerMapDao.saveUrlShortenerMap(mapEntry);
        return tinyUrlKey;
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

    public String getLengthyUrl(String tinyUrl) {
        UrlShortenerMap mapEntry = urlShortenerMapDao.getOneUrlShortenerMap(tinyUrl);
        if (null != mapEntry) {
            return mapEntry.getLongUrl();
        }
        return null;
    }
}
