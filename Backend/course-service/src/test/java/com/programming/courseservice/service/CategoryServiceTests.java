package com.programming.courseservice.service;

import com.main.progamming.common.error.exception.DataAlreadyExistException;
import com.main.progamming.common.error.exception.ResourceNotFoundException;
import com.main.progamming.common.message.StatusCode;
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

import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.*;
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

    // JUnit test for get all category method (negative scenario)
    @Test
    @DisplayName("JUnit test for get all category method (negative scenario)")
    public void givenEmptyEmployeeList_whenGetAll_thenReturnEmptyList() {
        // given - preconditions or setup
        when(categoryRepository.findAll()).thenReturn(Collections.emptyList());
        // when - action or behaviour that we are going test
        ListResponse<CategoryDto> response = categoryService.getAll();
        // then - verify the output
        assertThat(response).isNotNull();
        assertThat(response.getData()).isNull();
    }

    // JUnit test for get category by id method
    @Test
    @DisplayName("JUnit test for get category by id method")
    public void givenCategoryObject_whenGetById_thenCategoryObject() {
        // given - preconditions or setup
        Category category = new Category("1", "IT Software", "IT Software Description");
        when(categoryRepository.findById("1")).thenReturn(Optional.of(category));

        CategoryDto categoryDto = new CategoryDto("1", "IT Software", "IT Software Description", null);
        when(categoryMapper.entityToDto(category)).thenReturn(categoryDto);
        // when - action or behaviour that we are going test
        DataResponse<CategoryDto> response = categoryService.getById("1");
        // then - verify the output
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(StatusCode.REQUEST_SUCCESS);
        assertThat(response.getData()).isNotNull();
        assertThat(response.getData().getId()).isEqualTo("1"); // Đảm bảo id của CategoryDto đúng
    }

    // JUnit test for update category method
    @Test
    @DisplayName("JUnit test for update category method")
    public void givenCategoryDto_whenUpdateCategory_thenReturnCategoryDto() {
        // given - preconditions or setup
        String categoryId = "1";
        Category category = new Category("1", "IT Software", "IT Software Description");
        when(categoryRepository.findById("1")).thenReturn(Optional.of(category));
        CategoryDto categoryDto = new CategoryDto("1", "IT Software update", "IT Software Description update", null);

        when(categoryRepository.save(category)).thenReturn(category);
        when(categoryMapper.entityToDto(category)).thenReturn(categoryDto);
        // when - action or behaviour that we are going test
        DataResponse<CategoryDto> response = categoryService.update(categoryId, categoryDto);
        // then - verify the output
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(StatusCode.REQUEST_SUCCESS);
        assertThat(response.getData().getName()).isEqualTo("IT Software update");
        System.out.println(response.getData());
    }

    // JUnit test for getByName throw exception method
    @Test
    @DisplayName("JUnit test for getByName throw exception method")
    public void givenCategoryName_whenGetByName_thenThrowsException() {
        // given - preconditions or setup
        String categoryName = "IT Software";
        when(categoryRepository.findByName(categoryName)).thenReturn(Optional.empty());
        // when - action or behaviour that we are going test
        Throwable throwException = catchThrowable(() -> categoryService.getByName(categoryName));
        // then - verify the output
        assertThat(throwException).isInstanceOf(ResourceNotFoundException.class);
        assertThat(throwException.getMessage()).isEqualTo("Category with name " + categoryName + " does not exists in DB");
    }

    // JUnit test for getByName method
    @Test
    @DisplayName("JUnit test for getByName method")
    public void givenCategoryName_whenFindByName_thenReturnCategoryDto() {
        // given - preconditions or setup
        String categoryName = "IT Software";
        Category category = new Category("1", "IT Software", "IT Software Description");
        CategoryDto categoryDto = new CategoryDto("1", "IT Software", "IT Software Description", null);
        when(categoryRepository.findByName(categoryName)).thenReturn(Optional.of(category));
        when(categoryMapper.entityToDto(category)).thenReturn(categoryDto);
        // when - action or behaviour that we are going test
        DataResponse<CategoryDto> response = categoryService.getByName(categoryName);
        // then - verify the output
        System.out.println(response.getData());
        assertThat(response).isNotNull();
        assertThat(response.getData()).isEqualTo(categoryDto);
    }

    // JUnit test for getByName method throws exception
    @Test
    @DisplayName("JUnit test for getByName method throws exception")
    public void givenNonExistingCategoryName_whenFindByName_thenThrowsException() {
        // given - preconditions or setup
        String nonExistingCategoryName = "NonExistingCategoryName";
        when(categoryRepository.findByName(nonExistingCategoryName)).thenReturn(Optional.empty());
        // when - action or behaviour that we are going test
        Throwable throwException = catchThrowable(() -> categoryService.getByName(nonExistingCategoryName));
        // then - verify the output
        assertThat(throwException).isInstanceOf(ResourceNotFoundException.class);
        assertThat(throwException.getMessage()).isEqualTo("Category with name " + nonExistingCategoryName + " does not exists in DB");
    }
}
