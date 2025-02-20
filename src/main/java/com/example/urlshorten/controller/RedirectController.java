package com.example.urlshorten.controller;

import com.example.urlshorten.service.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;
import java.nio.file.Files;

@RestController
public class RedirectController {

    @Autowired
    private UrlService urlService;

    @Value("${app.base-url}")
    private String baseUrl;

    @GetMapping("/{shortUrl}")
    public ResponseEntity<?> redirectToOriginalUrl(@PathVariable String shortUrl) {
        if (shortUrl.equals("index.html")) {
            return serveIndexHtml();
        }

        try {
            String originalUrl = urlService.getOriginalUrl(shortUrl);
            return ResponseEntity.status(302).header("Location", originalUrl).build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    private ResponseEntity<?> serveIndexHtml() {
        try {
            Resource resource = new ClassPathResource("static/index.html");
            byte[] content = Files.readAllBytes(resource.getFile().toPath());

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_TYPE, "text/html");

            return new ResponseEntity<>(content, headers, HttpStatus.OK);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error loading index.html");
        }
    }
}
