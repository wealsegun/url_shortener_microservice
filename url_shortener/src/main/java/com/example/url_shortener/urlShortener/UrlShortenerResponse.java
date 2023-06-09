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
    private String urlName;
    private  String longUrl;
    private  String tinyUrl;
    private  String shortenedBitlyUrl;
    private  String userEmail;
    private  Boolean isCustomRequested;
    private  Integer clickedCount;
    private String customUrl;
    private  Date  expiryDate;
    private Date createdDate;
}
