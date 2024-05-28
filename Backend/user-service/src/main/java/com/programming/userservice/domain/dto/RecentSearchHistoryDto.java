package com.programming.userservice.domain.dto;

import com.main.progamming.common.dto.SearchConditionDto;
import com.programming.userservice.domain.persistent.enumrate.ModuleSearch;
import jakarta.persistence.Column;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
public class RecentSearchHistoryDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String id;

    private String username;

    private List<SearchConditionDto> searchChooseList;

    private List<SearchConditionDto> searchKeywordDtoList;

    private ModuleSearch moduleSearch;

    private Long created;
}
