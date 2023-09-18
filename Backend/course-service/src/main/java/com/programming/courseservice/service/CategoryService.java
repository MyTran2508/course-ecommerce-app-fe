package com.programming.courseservice.service;

import com.main.progamming.common.dto.SearchKeywordDto;
import com.main.progamming.common.error.exception.ResourceNotFoundException;
import com.main.progamming.common.model.BaseMapper;
import com.main.progamming.common.repository.BaseRepository;
import com.main.progamming.common.response.DataResponse;
import com.main.progamming.common.response.ResponseMapper;
import com.main.progamming.common.service.BaseServiceImpl;
import com.programming.courseservice.core.dto.CategoryDto;
import com.programming.courseservice.core.dto.TopicDto;
import com.programming.courseservice.core.mapper.CategoryMapper;
import com.programming.courseservice.core.mapper.TopicMapper;
import com.programming.courseservice.core.persistent.entity.Category;
import com.programming.courseservice.core.persistent.entity.Topic;
import com.programming.courseservice.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CategoryService extends BaseServiceImpl<Category, CategoryDto> {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final TopicMapper topicMapper;
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

    @Override
    public DataResponse<CategoryDto> update(String id, CategoryDto dto) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isEmpty()) {
            throw new ResourceNotFoundException(id + " does not exists in DB");
        }
        Category category = optionalCategory.get();
        categoryMapper.dtoToEntity(dto, category);
        category.getTopics().clear();
        for (TopicDto topicDto: dto.getTopics()) {
            category.getTopics().add(topicMapper.dtoToEntity(topicDto));
        }
        category.setId(id);
        categoryRepository.save(category);
        return ResponseMapper.toDataResponseSuccess("Success");
    }
}
