package com.example.url_shortener.url;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ShortCountRepository extends JpaRepository<ShortCount, Integer> {
    ShortCount getCountById(Integer id);

    @Query("SELECT COUNT(s) FROM ShortCount s WHERE s.userId = :userId")
    Integer getClickedCounts(@Param("userId") Integer userId);

}
