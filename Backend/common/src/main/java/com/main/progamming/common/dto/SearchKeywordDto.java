package com.main.progamming.common.dto;

import lombok.Data;

import java.util.List;

@Data
public class SearchKeywordDto {

    private List<String> keyword;

    private List<SearchKeywordDto> searchKeywordDtoList;

    private String sortBy = "";

    private Boolean isDecrease = true;

    private int pageIndex;

    private int pageSize;
}
