package com.example.url_shortener.url;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface URLShortenerRepository extends JpaRepository<Shortener, Integer> {
    Shortener getShortenerById(Integer id);
    Shortener getLongUrlByAlias(String aliasName);
    Boolean getCustomUrl(String customUrl);
    List<Shortener> getShortenerByUserEmail(String userEmail);
}
