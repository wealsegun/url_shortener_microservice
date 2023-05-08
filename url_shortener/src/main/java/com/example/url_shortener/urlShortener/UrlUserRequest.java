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
public class UrlUserRequest {
    private String urlName;
    private  String longUrl;
    private  String userEmail;
    private  Boolean isCustomRequested;
}
