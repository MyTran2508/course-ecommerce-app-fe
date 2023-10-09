package com.programming.courseservice.domain.mapper;

import com.main.progamming.common.model.BaseMapperImpl;
import com.programming.courseservice.domain.dto.CategoryDto;
import com.programming.courseservice.domain.persistent.entity.Category;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class CategoryMapper extends BaseMapperImpl<Category, CategoryDto> {
    public CategoryMapper(ModelMapper modelMapper) {
        super(modelMapper);
    }
    @Override
    protected Class<Category> getEntityClass() {
        return Category.class;
    }
    @Override
    protected Class<CategoryDto> getDtoClass() {
        return CategoryDto.class;
    }
}
