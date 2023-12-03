package com.learnings.urlshortener.dao;

import com.learnings.urlshortener.model.UrlShortenerMap;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class UrlShortenerMapDaoImpl implements UrlShortenerMapDao{

    private final String hashReference= "UrlShortenerMap";

    @Resource(name="urlShortenerMapTemplate")
    private HashOperations<String, String, UrlShortenerMap> hashOperations;


    @Override
    public void saveUrlShortenerMap(UrlShortenerMap urlShortenerMap) {
        hashOperations.putIfAbsent(hashReference, urlShortenerMap.getTinyUrl(), urlShortenerMap);
    }

    @Override
    public UrlShortenerMap getOneUrlShortenerMap(String tinyKey) {
        return hashOperations.get(hashReference, tinyKey);
    }

    @Override
    public void updateUrlShortenerMap(UrlShortenerMap urlShortenerMap) {
        hashOperations.put(hashReference, urlShortenerMap.getTinyUrl(), urlShortenerMap);
    }

    @Override
    public Map<String, UrlShortenerMap> getAllUrlShortenerMaps() {
        return hashOperations.entries(hashReference);
    }

    @Override
    public void deleteUrlShortenerMap(String tinyKey) {
        hashOperations.delete(hashReference, tinyKey);
    }

    @Override
    public void saveAllUrlShortenerMaps(Map<String, UrlShortenerMap> map) {
        hashOperations.putAll(hashReference, map);
    }
}
