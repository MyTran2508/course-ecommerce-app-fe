package com.programming.userservice.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SearchConditionDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String keyword;

    private Integer keywordType;
}
