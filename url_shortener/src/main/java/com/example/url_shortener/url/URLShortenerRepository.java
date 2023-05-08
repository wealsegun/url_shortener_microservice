package com.example.url_shortener.url;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface URLShortenerRepository extends JpaRepository<Shortener, Integer> {
    Shortener getShortenerById(Integer id);
    List<Shortener> getShortenerByUserEmail(String userEmail);
    Shortener findByAlias(String alias);


}
