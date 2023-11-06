package com.main.progamming.common.dto;

import lombok.Data;

@Data
public class SearchKeywordDto {
    private String keyword;
    private String sortBy = "";
    private Boolean isDecrease = true;
    private int pageIndex;
    private int pageSize;
}
