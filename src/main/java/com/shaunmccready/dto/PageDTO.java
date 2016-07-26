package com.shaunmccready.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


import java.util.List;



public class PageDTO<T> {

    @JsonInclude(Include.NON_NULL)
    private Integer pageNumber;

    @JsonInclude(Include.NON_NULL)
    private Integer pageSize;

    @JsonInclude(Include.NON_NULL)
    private Integer totalPages;

    @JsonInclude(Include.NON_NULL)
    private Long totalElements;

    @JsonInclude(Include.NON_NULL)
    private List<T> entities;

    public PageDTO() {
    }

    public PageDTO(Integer pageNumber, Integer pageSize, Integer totalPages, Long totalElements, List<T> entities) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
        this.entities = entities;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public List<T> getEntities() {
        return entities;
    }

}

