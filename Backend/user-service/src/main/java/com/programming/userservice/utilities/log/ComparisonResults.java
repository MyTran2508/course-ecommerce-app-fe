package com.programming.userservice.utilities.log;

import lombok.Data;

import java.util.List;

@Data
public class ComparisonResults {
    List<String> fieldNameList;

    List<String> oldValueList;

    List<String> newValueList;
}
