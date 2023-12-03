package com.learnings.urlshortener.configuration;

import com.learnings.urlshortener.model.UrlShortenerMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.RedisConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.Duration;

@Configuration
public class RedisConfig {

    @Autowired
    private Environment env;

    @Bean
    public RedisConfiguration defaultRedisConfig() {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
        String redisPort = env.getProperty("spring.redis.port");
        String redisPassword =  env.getProperty("spring.redis.password");
        String redisHost =  env.getProperty("spring.redis.host");
        config.setHostName(redisHost);
        config.setPassword(RedisPassword.of(redisPassword));
        config.setPort(Integer.parseInt(redisPort));
        config.setDatabase(0);
        return config;
    }

    @Bean
    @Primary
    public RedisConnectionFactory redisConnectionFactory(RedisConfiguration defaultRedisConfig) {

        LettuceClientConfiguration clientConfig = LettucePoolingClientConfiguration.builder()
                .commandTimeout(Duration.ofMillis(60000)).build();
        return new LettuceConnectionFactory(defaultRedisConfig, clientConfig);
    }

    @Bean
    public RedisTemplate<String, UrlShortenerMap> urlShortenerMapTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, UrlShortenerMap> urlShortenerMapTemplate = new RedisTemplate<>();
        urlShortenerMapTemplate.setConnectionFactory(redisConnectionFactory);
        return urlShortenerMapTemplate;
    }

}
