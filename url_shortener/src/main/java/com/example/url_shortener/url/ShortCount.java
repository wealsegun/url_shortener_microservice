package com.example.url_shortener.url;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private  Integer userId;
    private  String  aliasName;
    private  String  clickUrlName;
    private Integer  noOfClicked;
    private String   ClickedLink;

}
