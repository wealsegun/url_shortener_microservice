package com.example.url_shortener.url;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShortCountRepository extends JpaRepository<ShortCount, Integer> {
    List<ShortCount> findAllByUserEmailAndUrl(String userEmail, String clickedLink);

}
