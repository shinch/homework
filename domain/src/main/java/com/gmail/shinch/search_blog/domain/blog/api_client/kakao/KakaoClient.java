package com.gmail.shinch.search_blog.domain.blog.api_client.kakao;

import com.gmail.shinch.search_blog.domain.blog.api_client.ClientType;
import com.gmail.shinch.search_blog.domain.blog.api_client.ExternalApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Slf4j
@Component
public class KakaoClient {

    private final RestTemplate restTemplate;
    private final String apiHost;
    private final String apiKey;

    @Autowired
    public KakaoClient(
            @Value("${api.kakao.host}") final String apiHost,
            @Value("${api.kakao.api-key}") final String apiKey,
            RestTemplate restTemplate ) {
        this.restTemplate = restTemplate;
        this.apiHost = apiHost;
        this.apiKey = "KakaoAK " + apiKey;
    }

    public KakaoSearchReceive getSearchBlog(KakaoSearchSend kakaoSearchSend) {
        String apiPath = "/v2/search/blog";
        MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("query", kakaoSearchSend.getQuery());
        paramMap.add("sort", kakaoSearchSend.getSort());
        paramMap.add("page", Integer.toString(kakaoSearchSend.getPage()));
        paramMap.add("size", Integer.toString(kakaoSearchSend.getSize()));
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString("https://" + apiHost + apiPath);
        URI useUri = builder.queryParams(paramMap).build().encode().toUri();

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.set("Authorization", apiKey);
        HttpEntity requestEntity = new HttpEntity<>(requestHeaders);

        ParameterizedTypeReference responseType = new ParameterizedTypeReference<KakaoSearchReceive>() {};

        try {
            ResponseEntity<KakaoSearchReceive> response = restTemplate.exchange(useUri, HttpMethod.GET, requestEntity, responseType);
            return response.getBody();
        } catch ( HttpStatusCodeException ex ) {
            throw new ExternalApiException(ClientType.KAKAO, HttpStatus.valueOf(ex.getStatusCode().value()), ex.getMessage());
        } catch ( Exception ex ) {
            throw new ExternalApiException(ClientType.KAKAO, HttpStatus.BAD_GATEWAY, ex.getMessage());
        }
    }

}
