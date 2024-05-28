package com.programming.userservice.domain.dto;

import com.main.progamming.common.dto.SearchConditionDto;
import com.main.progamming.common.dto.SearchKeywordDto;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
public class SearchOrderDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private List<SearchConditionDto> searchChooseList;

    private List<SearchConditionDto> searchKeywordDtoList;

    private Double minPrice;

    private Double maxPrice;

    private Double price;

    private Long startDate;

    private Long endDate;

    private int pageIndex;

    private int pageSize;

    private String sortBy = "created";

    private Boolean isDecrease = true;
}
