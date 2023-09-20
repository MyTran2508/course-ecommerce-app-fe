package com.programming.courseservice.service;

import com.main.progamming.common.error.exception.DataAlreadyExistException;
import com.main.progamming.common.error.exception.ResourceNotFoundException;
import com.main.progamming.common.response.DataResponse;
import com.main.progamming.common.response.ListResponse;
import com.programming.courseservice.core.dto.CategoryDto;
import com.programming.courseservice.core.dto.TopicDto;
import com.programming.courseservice.core.mapper.CategoryMapper;
import com.programming.courseservice.core.persistent.entity.Category;
import com.programming.courseservice.core.persistent.entity.Topic;
import com.programming.courseservice.repository.CategoryRepository;
import static org.assertj.core.api.Assertions.assertThat;

import org.antlr.stringtemplate.language.Cat;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTests {
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private CategoryMapper categoryMapper;
    @InjectMocks
    private CategoryService categoryService;
    private CategoryDto categoryDto;
    @BeforeEach
    public void setup() {
        categoryDto = CategoryDto.builder()
                .name("IT Software")
                .description("IT Software description")
                .build();
        TopicDto topic1 = new TopicDto("Java", "Java Description");
        List<TopicDto> topicDtos = new ArrayList<>();
        topicDtos.add(topic1);
        categoryDto.setTopics(topicDtos);
    }

    // JUnit test for save category method
    @Test
    @DisplayName("JUnit test for save category method")
    public void givenCategoryObject_whenSaveEmployee_thenReturnCategoryObject() {
        // given - preconditions or setup
        Category category = new Category();
        when(categoryMapper.dtoToEntity(categoryDto)).thenReturn(category);
        when(categoryRepository.save(category)).thenReturn(category);
        when(categoryMapper.entityToDto(category)).thenReturn(categoryDto);
        // when - action or behaviour that we are going test
        DataResponse<CategoryDto> response = categoryService.create(categoryDto);
        System.out.println(response.getData());
        // then - verify the output
        assertThat(response).isNotNull();
        assertThat(response.getData()).isNotNull();
        assertThat(response.getData().getTopics().size()).isEqualTo(1);
    }

    // JUnit test for save category method which throws exception
    @Test
    @DisplayName("JUnit test for save category method which throws exception")
    public void givenExistingEmail_whenSaveEmployee_thenThrowsException() {
        // given - preconditions or setup
        Category category = new Category();
        given(categoryMapper.dtoToEntity(categoryDto)).willReturn(category);
        given(categoryRepository.save(category)).willThrow(DataIntegrityViolationException.class);

        // when - action or behaviour that we are going test
        Throwable exception = Assertions.assertThrows(DataAlreadyExistException.class, () -> {
            categoryService.create(categoryDto);
        });
        // then - verify the output
        verify(categoryRepository).save(category);
        assertThat(exception.getMessage()).isEqualTo("Data already exist");
    }

    // JUnit test for get all category method
    @Test
    public void givenCategoryList_whenGetAll_thenReturnCategoryList() {
        // given - preconditions or setup
        Category category1 = new Category("1", "IT Software", "IT Software Description");
        Category category2 = new Category("1", "Network", "Network Description");
        List<Category> categoryList = Arrays.asList(category1, category2);
        when(categoryRepository.findAll()).thenReturn(categoryList);

        CategoryDto categoryDto1 = new CategoryDto("1", "IT Software", "IT Software Description", null);
        CategoryDto categoryDto2 = new CategoryDto("1", "Network", "Network Description", null);
        List<CategoryDto> categoryDtoList = Arrays.asList(categoryDto1, categoryDto2);
        when(categoryMapper.entityToDto(any(Category.class))).thenAnswer(invocation -> {
            Category category = invocation.getArgument(0);
            return categoryDtoList.stream()
                    .filter(dto -> dto.getId().equals(category.getId()))
                    .findFirst()
                    .orElse(null);
        });

        // when - action or behaviour that we are going test
        ListResponse<CategoryDto> response = categoryService.getAll();
        // then - verify the output
        assertThat(response).isNotNull();
        assertThat(response.getData()).isNotNull();
        assertThat(response.getData()).hasSize(2);
    }
}
