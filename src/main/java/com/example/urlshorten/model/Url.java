package com.example.urlshorten.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "url")
public class Url {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "original_url")
    private String originalUrl;
    @Column(name = "short_url")
    private String shortUrl;
    @Column(name = "access_count")
    private int accessCount = 0;
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
}
