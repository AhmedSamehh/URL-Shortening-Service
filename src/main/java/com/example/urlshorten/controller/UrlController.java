package com.example.urlshorten.controller;

import com.example.urlshorten.model.Url;
import com.example.urlshorten.service.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/url")
public class UrlController {
    @Autowired
    private UrlService urlService;

    @Value("${app.base-url}")
    private String baseUrl;

    @PostMapping("/shorten")
    public ResponseEntity<Url> shortenUrl(@RequestBody Map<String,String> urlMap) {
        Url createdUrl = urlService.createShortUrl(urlMap.get("originalUrl"));
        String fullShortUrl = baseUrl + createdUrl.getShortUrl();
        createdUrl.setShortUrl(fullShortUrl);
        return ResponseEntity.ok(createdUrl);
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
}
