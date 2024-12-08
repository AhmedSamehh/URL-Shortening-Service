package com.example.urlshorten.service;

import com.example.urlshorten.model.Url;
import com.example.urlshorten.repository.UrlRepository;
import com.example.urlshorten.util.UrlValidationUtil;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

public class UrlService {

    private static final String BASE_SHORT_URL = "https://short.ly/";
    @Autowired
    private UrlRepository urlRepository;

    public Url createShortUrl(String urlToShorten) {
        if (!UrlValidationUtil.isValidUrl(urlToShorten)) {
            throw new IllegalArgumentException("Invalid URL provided");
        }

        Url url = new Url();
        url.setOriginalUrl(urlToShorten);
        url.setShortUrl(BASE_SHORT_URL + generateUniqueShortUrl());
        url.setCreatedAt(LocalDateTime.now());
        return urlRepository.save(url);
    }

    private String generateUniqueShortUrl() {
        String shortUrlId;
        do {
            shortUrlId = RandomStringUtils.randomAlphanumeric(8);
        } while (urlRepository.findByShortUrl(BASE_SHORT_URL + shortUrlId).isPresent());
        return shortUrlId;
    }
}
