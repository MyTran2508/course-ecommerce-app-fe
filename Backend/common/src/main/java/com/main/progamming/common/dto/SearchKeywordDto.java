package com.main.progamming.common.dto;

import lombok.Data;

import java.util.List;

@Data
public class SearchKeywordDto {

    private List<String> keyword;

    private List<SearchConditionDto> searchChooseList;

    private List<SearchConditionDto> searchKeywordDtoList;

    private String sortBy = "";

    private Boolean isDecrease = true;

    private int pageIndex;

    private int pageSize;

    // using in search course admin
    private Double price;

    private Double minPrice;

    private Double maxPrice;
}
