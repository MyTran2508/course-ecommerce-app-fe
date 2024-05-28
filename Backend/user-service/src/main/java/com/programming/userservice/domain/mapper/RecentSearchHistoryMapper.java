package com.programming.userservice.domain.mapper;

import com.main.progamming.common.model.BaseMapperImpl;
import com.programming.userservice.domain.dto.RecentSearchHistoryDto;
import com.programming.userservice.domain.persistent.entity.RecentSearchHistory;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class RecentSearchHistoryMapper extends BaseMapperImpl<RecentSearchHistory, RecentSearchHistoryDto> {

    public RecentSearchHistoryMapper(ModelMapper modelMapper) {
        super(modelMapper);
    }

    @Override
    protected Class<RecentSearchHistory> getEntityClass() {
        return RecentSearchHistory.class;
    }

    @Override
    protected Class<RecentSearchHistoryDto> getDtoClass() {
        return RecentSearchHistoryDto.class;
    }
}
