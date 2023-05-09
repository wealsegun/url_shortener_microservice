package com.example.url_shortener.url;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

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
    @Column(length = Integer.MAX_VALUE) // Set the length to maximum
    private String longUrl;
    private  String tinyUrl;
    private  String shortenedBitlyUrl;
    private  String userEmail;
    private  boolean isCustomRequested;
    private String customUrl;
    private String alias;
    private  Date  expiryDate;
    private Date createdDate;

    public String getLongUrl(String alias) {
        Date currentDate = new Date(); // Get the current date
        if (isCustomRequested && alias.equals(this.alias) && expiryDate != null && expiryDate.after(currentDate)) {
            return longUrl;
        }
        return null;
    }
}


