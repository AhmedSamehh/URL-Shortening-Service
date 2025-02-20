package com.example.urlshorten.controller;

import com.example.urlshorten.model.Url;
import com.example.urlshorten.service.UrlService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/url")
public class UrlController {

    private static final Logger logger = LoggerFactory.getLogger(UrlController.class);

    @Autowired
    private UrlService urlService;

    @Value("${app.base-url}")
    private String baseUrl;

    @PostMapping("/shorten")
    public ResponseEntity<Map<String, String>> shortenUrl(@RequestBody Map<String, String> urlMap) {
        Url createdUrl = urlService.createShortUrl(urlMap.get("originalUrl"));
        String fullShortUrl = baseUrl + createdUrl.getShortUrl();

        Map<String, String> response = new HashMap<>();
        response.put("shortUrl", fullShortUrl);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{shortUrl}")
    public ResponseEntity<String> getOriginalUrl(@PathVariable String shortUrl) {
        String originalUrl = urlService.getOriginalUrl(shortUrl);
        return ResponseEntity.ok(originalUrl);
    }

    @GetMapping("/{shortUrl}/stats")
    public ResponseEntity<Map<String, Object>> getUrlStats(@PathVariable String shortUrl) {
        Map<String, Object> stats = urlService.getUrlStats(shortUrl);
        return ResponseEntity.ok(stats);
    }

    @DeleteMapping("/{shortUrl}")
    public ResponseEntity<Void> deleteShortUrl(String shortUrl) {
        urlService.deleteShortUrl(shortUrl);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{shortUrl}")
    public ResponseEntity<Url> updateOriginalUrl(@PathVariable String shortUrl, @RequestBody Map<String, String> urlMap) {
        Url updatedUrl = urlService.updateOriginalUrl(shortUrl, urlMap.get("originalUrl"));
        return ResponseEntity.ok(updatedUrl);
    }
}
