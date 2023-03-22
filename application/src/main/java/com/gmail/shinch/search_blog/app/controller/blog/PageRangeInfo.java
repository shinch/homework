package com.gmail.shinch.search_blog.app.controller.blog;

import com.gmail.shinch.search_blog.app.exception.BadRequestException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PageRangeInfo {
    public static PageRangeInfo ofRequest(String rangeInfo) {
        return ofRequest(rangeInfo, 10);
    }
    public static PageRangeInfo ofRequest(String rangeInfo, int contentsPerPage) {
        int usePage = 0;
        if ( rangeInfo.isEmpty() ) {
            usePage = 1;
        } else if ( !rangeInfo.startsWith("pages=") ) {
            // TODO : 400 Error
            throw new BadRequestException("조회할 Page 범위 형식이 올바르지 않습니다.", "pages=3");
        } else {
            try {
                usePage = Integer.parseInt(rangeInfo.substring(rangeInfo.indexOf("=") + 1, rangeInfo.length()));
            } catch ( Exception ex ) {
                throw new BadRequestException("조회할 Page 범위 형식이 올바르지 않습니다.", "pages=3");
            }
        }
        return new PageRangeInfo(usePage, contentsPerPage, 0, 0);
    }

    private int usePage;
    private int contentsPerPage;
    private int totalPage;
    private int totalContents;

    public void setByTotalContents(int totalContents) {
        this.totalContents = totalContents;
        this.totalPage = totalContents / contentsPerPage + (totalContents % contentsPerPage > 0 ? 1 : 0);
    }
    public String getPageRangeResponse() {
        return String.format("pages=%d/%d", usePage, totalPage);
    }
}
