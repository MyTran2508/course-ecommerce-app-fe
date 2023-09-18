package com.programming.courseservice.service;

import com.main.progamming.common.dto.SearchKeywordDto;
import com.main.progamming.common.model.BaseMapper;
import com.main.progamming.common.repository.BaseRepository;
import com.main.progamming.common.service.BaseServiceImpl;
import com.programming.courseservice.core.dto.CategoryDto;
import com.programming.courseservice.core.mapper.CategoryMapper;
import com.programming.courseservice.core.persistent.entity.Category;
import com.programming.courseservice.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService extends BaseServiceImpl<Category, CategoryDto> {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    @Override
    protected BaseRepository<Category> getBaseRepository() {
        return categoryRepository;
    }
    @Override
    protected BaseMapper<Category, CategoryDto> getBaseMapper() {
        return categoryMapper;
    }
    @Override
    protected Page<CategoryDto> getPageResults(SearchKeywordDto searchKeywordDto, Pageable pageable) {
        return null;
    }
    @Override
    protected List<CategoryDto> getListSearchResults(String keyword) {
        return null;
    }
}
