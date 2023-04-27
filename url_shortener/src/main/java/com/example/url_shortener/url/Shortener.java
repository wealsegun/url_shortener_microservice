package com.example.url_shortener.url;

import jakarta.persistence.*;
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
public class Shortener {
    @Id
    @GeneratedValue
    private  Integer id;
    private String urlName;
    private  String longUrl;
    private  String tinyUrl;
    private  String shortenedBitlyUrl;
    private  String userEmail;
    private  boolean isCustomRequested;
    private String customUrl;
    private  Date  expiryDate;
    private Date createdDate;


}


