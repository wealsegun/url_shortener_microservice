package com.example.url_shortener.urlShortener;

import com.example.url_shortener.url.Shortener;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ShortUrlervice {
    private final ShortUrlervice shortUrlRepository;

    public Optional<Shortener> getLongUrlByAlias(String alias) {
        return shortUrlRepository.getLongUrlByAlias(alias);
    }
}
