package com.programming.courseservice.core.mapper;

import com.main.progamming.common.model.BaseMapperImpl;
import com.programming.courseservice.core.dto.CategoryDto;
import com.programming.courseservice.core.persistent.entity.Category;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
