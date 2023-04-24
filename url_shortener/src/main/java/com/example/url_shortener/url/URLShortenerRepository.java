package com.example.url_shortener.url;

import java.util.List;

public interface URLShortener {
    String getShortUrl(String userEmail);
    boolean isCustomRequested(String userEmail);
    Shortener getShortenerById(Integer id);
    List<Shortener> getShortenerByUserEmail(String userEmail);
}
