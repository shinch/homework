package com.gmail.shinch.search_blog.domain.keyword.service;

import com.gmail.shinch.search_blog.domain.keyword.repository.KeywordEntity;
import com.gmail.shinch.search_blog.domain.keyword.repository.KeywordRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class KeywordService {
    private final KeywordRepository keywordRepository;
    private final KeywordMapper keywordMapper;
    public List<KeywordDto> getTopTeenKeyword() {
        List<KeywordEntity> keywordEntities = keywordRepository.findTop10ByOrderByCntDescKeywordAsc();
        List<KeywordDto> keywordDtos = keywordMapper.toKeywordDtos(keywordEntities);
        return keywordDtos;
    }

    @Transactional
    public void increaseCount(String keyword) {
        // 대다수가 있을것 이라고 판단 update 후 없을시 등록
        int applyCnt = keywordRepository.updateCount(keyword);
        if ( applyCnt < 1 ) {
            KeywordEntity keywordEntity = new KeywordEntity(keyword);
            keywordRepository.save(keywordEntity);
        }
    }

}
