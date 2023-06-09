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
public class ShortCountRequest {
    private  String userEmail;
    private  String  clickUrlName;
    private String   clickedLink;
    private Date dateCreated;
}
