package com.gmail.shinch.search_blog.domain.keyword.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface KeywordRepository extends JpaRepository<KeywordEntity, Long> {
    Optional<KeywordEntity> findByKeyword(String keyword);
    List<KeywordEntity> findTop10ByOrderByCntDescKeywordAsc();
    @Modifying
    @Query("UPDATE KeywordEntity SET cnt = cnt+1 WHERE keyword = :keyword")
    int updateCount(@Param("keyword") String keyword);
}
