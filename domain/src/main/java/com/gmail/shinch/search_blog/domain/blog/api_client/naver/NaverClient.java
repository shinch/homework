package com.gmail.shinch.search_blog.domain.blog.api_client.naver;

import com.gmail.shinch.search_blog.domain.blog.api_client.ClientType;
import com.gmail.shinch.search_blog.domain.blog.api_client.ExternalApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Slf4j
@Component
public class NaverClient {
    private final RestTemplate restTemplate;
    private final String apiHost;
    private final String clientId;
    private final String clientSecret;

    @Autowired
    public NaverClient(
            @Value("${api.naver.host}") final String apiHost,
            @Value("${api.naver.client-id}") final String clientId,
            @Value("${api.naver.client-secret}") final String clientSecret,
            RestTemplate restTemplate ) {
        this.restTemplate = restTemplate;
        this.apiHost = apiHost;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    public NaverSearchReceive getSearchBlog(NaverSearchSend naverSearchSend) {
        String apiPath = "/v1/search/blog.json";
        MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("query", naverSearchSend.getQuery());
        paramMap.add("sort", naverSearchSend.getSort());
        paramMap.add("start", Integer.toString(naverSearchSend.getStart()));
        paramMap.add("display", Integer.toString(naverSearchSend.getDisplay()));
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString("https://" + apiHost + apiPath);
        URI useUri = builder.queryParams(paramMap).build().encode().toUri();

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.set("X-Naver-Client-Id", clientId);
        requestHeaders.set("X-Naver-Client-Secret", clientSecret);
        HttpEntity requestEntity = new HttpEntity<>(requestHeaders);

        ParameterizedTypeReference responseType = new ParameterizedTypeReference<NaverSearchReceive>() {};

        try {
            ResponseEntity<NaverSearchReceive> response = restTemplate.exchange(useUri, HttpMethod.GET, requestEntity, responseType);
            return response.getBody();
        } catch ( HttpStatusCodeException ex ) {
            throw new ExternalApiException(ClientType.NAVER, HttpStatus.valueOf(ex.getStatusCode().value()), ex.getMessage());
        } catch ( Exception ex ) {
            throw new ExternalApiException(ClientType.NAVER, HttpStatus.BAD_GATEWAY, ex.getMessage());
        }
    }

}
