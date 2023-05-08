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

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "shortCount")
public class ShortCount {
    @Id
    @GeneratedValue
    private  Integer id;
    private  String userEmail;
    private  String clickUrlName;
    private String  clickedLink;
    private Date dateCreated;

}
