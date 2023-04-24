package com.example.url_shortener.url;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "shoteners")
public class Shortener implements URLShortener {
    @Id
    @GeneratedValue
    private  Integer id;
    private  String longUrl;
    private  String shortenedUrl;
    private  String userEmail;
    private  boolean isCustomRequested;
    private String customUrl;
    private Date expiryDate;

    @Override
    public String getShortUrl(String userEmail) { return  shortenedUrl;}

    @Override
    public boolean isCustomRequested(String userEmail) {
        return true;
    }

    @Override
    public Shortener getShortenerById(Integer id) {
        return null;
    }

    @Override
    public List<Shortener> getShortenerByUserEmail(String userEmail) {
        return null;
    }

}


