package com.gmail.shinch.search_blog.domain.blog.api_client.kakao;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class BlogMeta {
    @JsonProperty("is_end")
    private boolean end;
    @JsonProperty("pageable_count")
    private int pageableCount;
    @JsonProperty("total_count")
    private int totalCount;
}
