package com.learnings.urlshortener.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UrlShortenerMap implements Serializable {
    private static final long serialVersionUID = -7817224776021728682L;

    private String longUrl;
    private String tinyUrl;
}
