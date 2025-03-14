package com.example.urlshorten.service;

import com.example.urlshorten.model.Url;
import com.example.urlshorten.repository.UrlRepository;
import com.example.urlshorten.util.UrlValidationUtil;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class UrlService {

    private static final String BASE_SHORT_URL = "https://short.ly/";
    @Autowired
    private UrlRepository urlRepository;

    public Url createShortUrl(String urlToShorten) {
        if (!UrlValidationUtil.isValidUrl(urlToShorten)) {
            throw new IllegalArgumentException("Invalid URL provided");
        }

        Optional<Url> existingUrl = urlRepository.findByOriginalUrl(urlToShorten);

        if (existingUrl.isPresent()) {
            return existingUrl.get(); // Return the existing entry
        }

        Url url = new Url();
        url.setOriginalUrl(urlToShorten);
        url.setShortUrl(generateUniqueShortUrlId());
        url.setCreatedAt(LocalDateTime.now());
        return urlRepository.save(url);
    }

    private String generateUniqueShortUrlId() {
        String shortUrlId;
        do {
            shortUrlId = RandomStringUtils.randomAlphanumeric(8);
        } while (urlRepository.findByShortUrl(shortUrlId).isPresent());
        return shortUrlId;
    }

    public String getOriginalUrl(String shortUrlId) {
        if(shortUrlId.isEmpty())
            throw new RuntimeException("Short URL identifier not found cannot be empty");
        Url url = urlRepository.findByShortUrl(shortUrlId)
                .orElseThrow(() -> new RuntimeException("Short URL not found"));

        url.setAccessCount(url.getAccessCount() + 1);
        urlRepository.save(url);

        return url.getOriginalUrl();
    }

    public Map<String, Object> getUrlStats(String shortUrl) {
        Url url = urlRepository.findByShortUrl(shortUrl)
                .orElseThrow(() -> new RuntimeException("Short URL not found"));

        Map<String, Object> stats = new HashMap<>();
        stats.put("originalUrl", url.getOriginalUrl());
        stats.put("shortUrl", url.getShortUrl());
        stats.put("accessCount", url.getAccessCount());
        stats.put("createdAt", url.getCreatedAt());

        return stats;
    }

    public void deleteShortUrl(String shortUrl) {
        Url url = urlRepository.findByShortUrl(shortUrl)
                .orElseThrow(() -> new RuntimeException("Short URL not found"));

        urlRepository.delete(url);
    }

    public Url updateOriginalUrl(String shortUrl, String updatedOriginalUrl) {
        Url url = urlRepository.findByShortUrl(shortUrl)
                .orElseThrow(() -> new RuntimeException("Short URL not found"));

        url.setOriginalUrl(updatedOriginalUrl);
        return urlRepository.save(url);
    }
}
