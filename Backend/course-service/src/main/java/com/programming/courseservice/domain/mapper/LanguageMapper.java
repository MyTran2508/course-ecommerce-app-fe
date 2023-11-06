package com.programming.courseservice.domain.mapper;

import com.main.progamming.common.model.BaseMapperImpl;
import com.programming.courseservice.domain.dto.LanguageDto;
import com.programming.courseservice.domain.persistent.entity.Language;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class LanguageMapper extends BaseMapperImpl<Language, LanguageDto> {
    public LanguageMapper(ModelMapper modelMapper) {
        super(modelMapper);
    }

    @Override
    protected Class<Language> getEntityClass() {
        return Language.class;
    }

    @Override
    protected Class<LanguageDto> getDtoClass() {
        return LanguageDto.class;
    }
}
