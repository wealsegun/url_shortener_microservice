package com.example.url_shortener.urlShortener;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/url-shortener")
public class UrlShortenerController {
    @GetMapping
    public ResponseEntity<String> GenerateSHortUrlEncoder (String url) {
        return  ResponseEntity.ok(url);
    }
}
