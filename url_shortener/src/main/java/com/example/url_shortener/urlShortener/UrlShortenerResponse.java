package com.example.url_shortener.urlShortener;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UrlShortenerResponse {
    private  Integer id;
    private String UrlName;
    private  String longUrl;
    private  String tinyUrl;
    private  String shortenedBitlyUrl;
    private  String userEmail;
    private  boolean isCustomRequested;
    private String customUrl;
    private  Date  expiryDate;
    private Date createdDate;
}
