package com.isikef.club.response;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Setter
public class RPage {
    int pageNumber;
    int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean hasNext;
    private boolean hasPrevious;
    private List<?> data;

    public static RPage of(Page rpage){
        RPage page = new RPage();
        page.setPageSize(rpage.getSize());
        page.setPageNumber(rpage.getNumber());
        page.setTotalPages(rpage.getTotalPages());
        page.setTotalElements(rpage.getTotalElements());
        page.setHasNext(!rpage.isLast());
        page.setHasPrevious(!rpage.isFirst());
        page.setData(rpage.getContent());
        return page;
    }
}
