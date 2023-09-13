package com.main.ocean.common.dto;

import lombok.Data;

@Data
public class SearchKeywordDto {
    private String keyword;
    private int pageIndex;
    private int pageSize;
}
