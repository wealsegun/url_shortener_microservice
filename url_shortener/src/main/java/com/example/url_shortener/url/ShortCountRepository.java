package com.example.url_shortener.url;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ShortCountRepository extends JpaRepository<ShortCount, Integer> {
    ShortCount getCountById(Integer id);
    Integer getClickedCounts(Integer userId, String aliasName, String clickUrlName);

}
