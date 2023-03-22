package com.gmail.shinch.search_blog.domain.blog.service

import com.gmail.shinch.search_blog.domain.blog.api_client.kakao.BlogDocument
import com.gmail.shinch.search_blog.domain.blog.api_client.kakao.BlogMeta
import com.gmail.shinch.search_blog.domain.blog.api_client.kakao.KakaoSearchReceive
import com.gmail.shinch.search_blog.domain.blog.api_client.naver.NaverBlogItem
import com.gmail.shinch.search_blog.domain.blog.api_client.naver.NaverSearchReceive
import spock.lang.Specification

class BlogMapperTest extends Specification {
    def blogMapper

    def setup() {
        blogMapper = BlogMapper.INSTANCE
    }

    def "SearchDto를 KakaoSearchSend로 변경"() {
        given:
        def searchDto = SearchDto.builder()
            .keyword("keyworld")
            .orderType(OrderType.ACCURACY)
            .page(1)
            .size(10).build()

        when:
        def targetModel = blogMapper.toKakaoSearchSend(searchDto)

        then:
        targetModel.query == searchDto.keyword
        targetModel.sort == searchDto.orderType.kakaoType
        targetModel.page == searchDto.page
        targetModel.size == searchDto.size
    }

    def "SearchDto를 NaverSearchSend로 변경"() {
        given:
        def searchDto = SearchDto.builder()
                .keyword("keyworld")
                .orderType(OrderType.ACCURACY)
                .page(2)
                .size(10).build()

        when:
        def targetModel = blogMapper.toNaverSearchSend(searchDto)

        then:
        targetModel.query == searchDto.keyword
        targetModel.sort == searchDto.orderType.naverType
        targetModel.start == 11
        targetModel.display == searchDto.size
    }

    def "BlogDocument를 BlogDto로 변경"() {
        given:
        def blogDocument = new BlogDocument()
        blogDocument.blogname = "크로노"
        blogDocument.contents = "서귀포 <b>맛집</b> BEST 색달식당 방문기 안녕하세요 크로노입니다. 이번에 거의 몇년만에 가족들과 제주도 여행에 다녀왔습니다."
        blogDocument.datetime = "2023-03-08T13:44:19.000+09:00"
        blogDocument.thumbnail = "https://search4.kakaocdn.net/argon/130x130_85_c/B5EoggjDI3j"
        blogDocument.title = "서귀포 <b>맛집</b> BEST"
        blogDocument.url = "http://croboda.tistory.com/45"

        when:
        def targetDto = blogMapper.toBlogDtoByKakao(blogDocument)

        then:
        targetDto.name == blogDocument.blogname
        targetDto.title == "서귀포 맛집 BEST"
        targetDto.link == blogDocument.url
    }

    def "BlogDocument 목록을 BlogDto 목록으로 변경"() {
        given:
        def blogDocument = new BlogDocument()
        blogDocument.blogname = "크로노"
        blogDocument.contents = "서귀포 <b>맛집</b> BEST 색달식당 방문기 안녕하세요 크로노입니다. 이번에 거의 몇년만에 가족들과 제주도 여행에 다녀왔습니다."
        blogDocument.datetime = "2023-03-08T13:44:19.000+09:00"
        blogDocument.thumbnail = "https://search4.kakaocdn.net/argon/130x130_85_c/B5EoggjDI3j"
        blogDocument.title = "서귀포 <b>맛집</b> BEST"
        blogDocument.url = "http://croboda.tistory.com/45"
        def blogDocuments = List.of(blogDocument, blogDocument, blogDocument)

        when:
        def targetDtos = blogMapper.toBlogDtosByKakao(blogDocuments)

        then:
        targetDtos.size() == blogDocuments.size()
    }

    def "NaverBlogItem을 BlogDto로 변경"() {
        given:
        def naverBlogItem = new NaverBlogItem()
        naverBlogItem.title = "통영 꿀빵 <b>맛집</b>으로 소문난 명가꿀빵"
        naverBlogItem.link = "https://blog.naver.com/d_nb73/223025450666"
        naverBlogItem.description = "디저트로 먹었지만 퀄리티와 맛까지좋았던 통영 꿀빵 <b>맛집</b>이있어 소개해보려해요. 명가꿀빵 주소..."
        naverBlogItem.bloggername = "NABI WORLD"
        naverBlogItem.bloggerlink = "blog.naver.com/d_nb73"
        naverBlogItem.postdate = "20230223"

        when:
        def targetDto = blogMapper.toBlogDtoByNaver(naverBlogItem)

        then:
        targetDto.name == naverBlogItem.bloggername
        targetDto.title == "통영 꿀빵 맛집으로 소문난 명가꿀빵"
        targetDto.link == naverBlogItem.link
    }

    def "NaverBlogItem 목록을 BlogDto 목록으로 변경"() {
        given:
        def naverBlogItem = new NaverBlogItem()
        naverBlogItem.title = "통영 꿀빵 <b>맛집</b>으로 소문난 명가꿀빵"
        naverBlogItem.link = "https://blog.naver.com/d_nb73/223025450666"
        naverBlogItem.description = "디저트로 먹었지만 퀄리티와 맛까지좋았던 통영 꿀빵 <b>맛집</b>이있어 소개해보려해요. 명가꿀빵 주소..."
        naverBlogItem.bloggername = "NABI WORLD"
        naverBlogItem.bloggerlink = "blog.naver.com/d_nb73"
        naverBlogItem.postdate = "20230223"
        def naverBlogItems = List.of(naverBlogItem, naverBlogItem, naverBlogItem)

        when:
        def targetDtos = blogMapper.toBlogDtosByNaver(naverBlogItems)

        then:
        targetDtos.size() == naverBlogItems.size()
    }

    def "KakaoSearchReceive를 SearchBlogResultDto로 변경"() {
        given:
        def blogDocument = new BlogDocument()
        blogDocument.blogname = "크로노"
        blogDocument.contents = "서귀포 <b>맛집</b> BEST 색달식당 방문기 안녕하세요 크로노입니다. 이번에 거의 몇년만에 가족들과 제주도 여행에 다녀왔습니다."
        blogDocument.datetime = "2023-03-08T13:44:19.000+09:00"
        blogDocument.thumbnail = "https://search4.kakaocdn.net/argon/130x130_85_c/B5EoggjDI3j"
        blogDocument.title = "서귀포 <b>맛집</b> BEST"
        blogDocument.url = "http://croboda.tistory.com/45"
        def blogDocuments = List.of(blogDocument, blogDocument, blogDocument)
        def meta = new BlogMeta()
        meta.setEnd(true);
        meta.setPageableCount(10)
        meta.setTotalCount(1234)
        def kakaoSearchReceive = new KakaoSearchReceive()
        kakaoSearchReceive.setDocuments(blogDocuments)
        kakaoSearchReceive.setMeta(meta)

        when:
        def targetDto = blogMapper.toSearchBlogResultDtoByKakao(kakaoSearchReceive)

        then:
        targetDto.blogs.size() == kakaoSearchReceive.documents.size()
        targetDto.totalCount == kakaoSearchReceive.meta.totalCount
    }

    def "NaverSearchReceive를 SearchBlogResultDto로 변경"() {
        given:
        def naverBlogItem = new NaverBlogItem()
        naverBlogItem.title = "통영 꿀빵 <b>맛집</b>으로 소문난 명가꿀빵"
        naverBlogItem.link = "https://blog.naver.com/d_nb73/223025450666"
        naverBlogItem.description = "디저트로 먹었지만 퀄리티와 맛까지좋았던 통영 꿀빵 <b>맛집</b>이있어 소개해보려해요. 명가꿀빵 주소..."
        naverBlogItem.bloggername = "NABI WORLD"
        naverBlogItem.bloggerlink = "blog.naver.com/d_nb73"
        naverBlogItem.postdate = "20230223"
        def naverBlogItems = List.of(naverBlogItem, naverBlogItem, naverBlogItem)
        def naverSearchReceive = new NaverSearchReceive()
        naverSearchReceive.items = naverBlogItems
        naverSearchReceive.lastBuildDate = "Sun, 19 Mar 2023 17:16:44 +0900"
        naverSearchReceive.total = 1234
        naverSearchReceive.start = 10
        naverSearchReceive.display = 15

        when:
        def targetDto = blogMapper.toSearchBlogResultDtoByNaver(naverSearchReceive)

        then:
        targetDto.blogs.size() == naverSearchReceive.items.size()
        targetDto.totalCount == naverSearchReceive.total
    }
}
