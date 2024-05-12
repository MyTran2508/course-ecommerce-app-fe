package com.programming.userservice.domain.dto;

import com.programming.userservice.domain.persistent.enumrate.ModuleSearch;
import jakarta.persistence.Column;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Data
public class RecentSearchHistoryDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String id;

    private String username;

    private Integer keywordType;

    private String keyword;

    private ModuleSearch moduleSearch;

    private Long created;
}
