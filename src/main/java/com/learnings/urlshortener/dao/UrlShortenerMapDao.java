package com.learnings.urlshortener.dao;

import com.learnings.urlshortener.model.UrlShortenerMap;

import java.util.Map;

public interface UrlShortenerMapDao {
    void saveUrlShortenerMap(UrlShortenerMap emp);
    UrlShortenerMap getOneUrlShortenerMap(String tinyKey);
    void updateUrlShortenerMap(UrlShortenerMap emp);
    Map<String, UrlShortenerMap> getAllUrlShortenerMaps();
    void deleteUrlShortenerMap(String tinyKey);
    void saveAllUrlShortenerMaps(Map<String, UrlShortenerMap> map);
}
