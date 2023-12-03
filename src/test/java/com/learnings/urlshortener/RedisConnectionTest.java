package com.learnings.urlshortener;

import com.learnings.urlshortener.dao.UrlShortenerMapDao;
import com.learnings.urlshortener.model.UrlShortenerMap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RedisConnectionTest {

	@Autowired
	private UrlShortenerMapDao urlShortenerMapDao;

	@Test
	void testRedisConnection() {
		UrlShortenerMap expectedEntry = new UrlShortenerMap("http://www.example.com/", "key");

		urlShortenerMapDao.saveUrlShortenerMap(expectedEntry);
		UrlShortenerMap actualEntry = urlShortenerMapDao.getOneUrlShortenerMap(expectedEntry.getTinyUrl());
		Assertions.assertEquals(expectedEntry.getTinyUrl(), actualEntry.getTinyUrl());
		Assertions.assertEquals(expectedEntry.getLongUrl(), actualEntry.getLongUrl());

		expectedEntry.setLongUrl("http://www.example.com/update");
		//modifying entry with update
		urlShortenerMapDao.updateUrlShortenerMap(expectedEntry);
		UrlShortenerMap actualUpdatedEntry = urlShortenerMapDao.getOneUrlShortenerMap(expectedEntry.getTinyUrl());
		Assertions.assertEquals(expectedEntry.getTinyUrl(), actualUpdatedEntry.getTinyUrl());
		Assertions.assertEquals(expectedEntry.getLongUrl(), actualUpdatedEntry.getLongUrl());

		//deleting entry with key
		urlShortenerMapDao.deleteUrlShortenerMap(expectedEntry.getTinyUrl());
		Assertions.assertNull(urlShortenerMapDao.getOneUrlShortenerMap(expectedEntry.getTinyUrl()));
	}

}
