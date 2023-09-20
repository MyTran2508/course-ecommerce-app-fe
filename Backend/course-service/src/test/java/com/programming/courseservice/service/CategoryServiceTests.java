package com.programming.courseservice.service;

import com.main.progamming.common.response.DataResponse;
import com.programming.courseservice.core.dto.CategoryDto;
import com.programming.courseservice.core.dto.TopicDto;
import com.programming.courseservice.core.mapper.CategoryMapper;
import com.programming.courseservice.core.persistent.entity.Category;
import com.programming.courseservice.core.persistent.entity.Topic;
import com.programming.courseservice.repository.CategoryRepository;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.lenient;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTests {
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private CategoryMapper categoryMapper;
    @InjectMocks
    private CategoryService categoryService;

    // JUnit test for save category method
    @Test
    @DisplayName("JUnit test for save category method")
    public void givenCategoryObject_whenSaveEmployee_thenReturnCategoryObject() {
        // given - preconditions or setup
        CategoryDto categoryDto = CategoryDto.builder()
                .name("IT Software")
                .description("IT Software description")
                .build();
        TopicDto topic1 = new TopicDto("Java", "Java Description");
        List<TopicDto> topicDtos = new ArrayList<>();
        topicDtos.add(topic1);
        categoryDto.setTopics(topicDtos);

        lenient().when(categoryRepository.findByName(categoryDto.getName()))
                .thenReturn(Optional.empty());
        Category category = new Category();
        given(categoryMapper.dtoToEntity(categoryDto)).willReturn(category);
        given(categoryRepository.save(category)).willReturn(category);
        given(categoryMapper.entityToDto(category)).willReturn(categoryDto);
        // when - action or behaviour that we are going test
        DataResponse<CategoryDto> response = categoryService.create(categoryDto);
        // then - verify the output
        assertThat(response).isNotNull();
        assertThat(response.getData()).isNotNull();
        assertThat(response.getData().getTopics().size()).isEqualTo(1);
    }
}
