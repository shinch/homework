package com.gmail.shinch.search_blog.domain.keyword.repository;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "keyword")
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class KeywordEntity {
    public KeywordEntity(String keyword) {
        this.keyword = keyword;
        this.cnt = 1;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seq", nullable = false)
    private Long seq; //자동 증가 일련번호

    @Column(name = "keyword", length=150, nullable = false)
    private String keyword; //검색어

    @Column(name = "cnt", nullable = false)
    private Integer cnt; //조회 횟수
}
