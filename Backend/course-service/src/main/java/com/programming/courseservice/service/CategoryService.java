package com.programming.courseservice.service;

import com.main.progamming.common.dto.SearchKeywordDto;
import com.main.progamming.common.error.exception.ResourceNotFoundException;
import com.main.progamming.common.model.BaseMapper;
import com.main.progamming.common.repository.BaseRepository;
import com.main.progamming.common.response.DataResponse;
import com.main.progamming.common.response.ResponseMapper;
import com.main.progamming.common.service.BaseServiceImpl;
import com.programming.courseservice.domain.dto.CategoryDto;
import com.programming.courseservice.domain.dto.TopicDto;
import com.programming.courseservice.domain.mapper.CategoryMapper;
import com.programming.courseservice.domain.mapper.TopicMapper;
import com.programming.courseservice.domain.persistent.entity.Category;
import com.programming.courseservice.domain.persistent.entity.Topic;
import com.programming.courseservice.repository.CategoryRepository;
import com.programming.courseservice.utilities.constant.CourseConstrant;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        return super.update(id, dto);
    }

    //    @Override
//    @Transactional
//    public DataResponse<CategoryDto> update(String id, CategoryDto categoryDto) {
//        Optional<Category> optionalCategory = categoryRepository.findById(id);
//        if (optionalCategory.isEmpty()) {
//            throw new ResourceNotFoundException(id + " does not exists in DB");
//        }
//        Category category = optionalCategory.get();
//        List<Topic> topics = categoryDto.getTopics().stream()
//                .map(topicDto -> topicMapper.dtoToEntity(topicDto))
//                .collect(Collectors.toList());
//        category.setTopicsAll(topics);
//        category.setName(categoryDto.getName());
//        category.setDescription(categoryDto.getDescription());
//        category.getTopics().forEach(System.out::println);
//        Category updatedCategory = categoryRepository.save(category);
//        return ResponseMapper.toDataResponseSuccess(categoryMapper.entityToDto(updatedCategory));
//    }
//    @Override
//    @Transactional
//    public DataResponse<CategoryDto> update(String id, CategoryDto dto) {
//        Optional<Category> optionalCategory = categoryRepository.findById(id);
//        if (optionalCategory.isEmpty()) {
//            throw new ResourceNotFoundException(id + " does not exists in DB");
//        }
//        Category category = optionalCategory.get();
//        category.setName(dto.getName());
//        category.setDescription(dto.getDescription());
//        category.setNewTopics(setNewTopics(category, dto.getTopics()));
//        category.setId(id);
//        Category updatedCategory = categoryRepository.save(category);
//        return ResponseMapper.toDataResponseSuccess(categoryMapper.entityToDto(updatedCategory));
//    }
//    public List<Topic> setNewTopics(Category category, List<TopicDto> topicDtos) {
//        List<Topic> results = new ArrayList<>();
//        List<Topic> topicsInDb = category.getTopics();
//        if(topicDtos == null)
//            return null;
//        for (TopicDto topicDto: topicDtos) {
//            Topic topic;
//            if(topicDto.getId() != null) {
//                topic = topicsInDb.stream()
//                        .filter(value -> value.getId().equals(topicDto.getId()))
//                        .findFirst()
//                        .get();
//                topicMapper.dtoToEntity(topicDto, topic);
//            } else {
//                topic = topicMapper.dtoToEntity(topicDto);
//            }
//            topic.setCategory(category);
//            results.add(topic);
//        }
//        return results;
//    }
    public DataResponse<CategoryDto> getByName(String name) {
        Optional<Category> optionalCategory = categoryRepository.findByName(name);

        if(optionalCategory.isEmpty()) {
            throw new ResourceNotFoundException(CourseConstrant.ErrorConstrant.NAME_NOT_FOUND);
        }

        Category category = optionalCategory.get();
        return ResponseMapper.toDataResponseSuccess(categoryMapper.entityToDto(category));
    }
}
