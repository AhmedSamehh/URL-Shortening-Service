package com.example.urlshorten.controller;

import com.example.urlshorten.model.Url;
import com.example.urlshorten.service.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/url")
public class UrlController {
    @Autowired
    private UrlService urlService;

    @PostMapping("/shorten")
    public ResponseEntity<Url> shortenUrl(@RequestBody Map<String,String> urlMap) {
        Url createdUrl = urlService.createShortUrl(urlMap.get("originalUrl"));
        return ResponseEntity.ok(createdUrl);
    }
}
