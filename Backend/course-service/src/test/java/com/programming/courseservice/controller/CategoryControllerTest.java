package com.programming.courseservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.main.progamming.common.response.DataResponse;
import com.main.progamming.common.response.ListResponse;
import com.main.progamming.common.response.ResponseMapper;
import com.programming.courseservice.domain.dto.CategoryDto;
import com.programming.courseservice.service.CategoryService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(CategoryController.class)
public class CategoryControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CategoryService categoryService;
    @Autowired
    private ObjectMapper objectMapper;

    // JUnit test for rest api create category object
    @Test
    @DisplayName("JUnit test for rest api create category object")
    public void givenCategoryObject_whenCreateCategory_thenReturnCategoryObject() throws Exception {
        // given - preconditions or setup
        CategoryDto categoryDto = new CategoryDto("1", "IT Software", "IT Software Description", null);
        DataResponse<CategoryDto> responseData = ResponseMapper.toDataResponseSuccess(categoryDto);

        when(categoryService.create(any(CategoryDto.class))).thenReturn(responseData);
        // when - action or behaviour that we are going test
        ResultActions response = mockMvc.perform(post("/api/courses/categories/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoryDto)));
        // then - verify the output
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.id", CoreMatchers.is(categoryDto.getId())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.name", CoreMatchers.is(categoryDto.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.description", CoreMatchers.is(categoryDto.getDescription())));
    }

    // JUnit test for rest api get all category
    @Test
    @DisplayName("JUnit test for rest api get all category ")
    public void givenListCategory_whenGetAll_thenListResponse() throws Exception {
        // given - preconditions or setup
        CategoryDto categoryDto = new CategoryDto("1", "IT Software", "IT Software Description", null);
        CategoryDto categoryDto2 = new CategoryDto("2", "IT Hardware", "IT Hardware Description", null);
        List<CategoryDto> categoryDtoList = List.of(categoryDto, categoryDto2);
        ListResponse<CategoryDto> responseData = ResponseMapper.toListResponseSuccess(categoryDtoList);

        when(categoryService.getAll()).thenReturn(responseData);
        // when - action or behaviour that we are going test
        ResultActions response = mockMvc.perform(get("/api/courses/categories/get-all"));
        // then - verify the output
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.size()", CoreMatchers.is(categoryDtoList.size())));
    }

    // JUnit test for rest api update category
    @Test
    @DisplayName("JUnit test for rest api update category")
    public void givenCategoryObject_whenUpdate_thenCategoryObject() throws Exception {
        // given - preconditions or setup
        String categoryId = "1";
        CategoryDto categoryDto = new CategoryDto("1", "IT Software", "IT Software Description", null);
        DataResponse<CategoryDto> responseData = ResponseMapper.toDataResponseSuccess(categoryDto);
        when(categoryService.update(categoryId, categoryDto)).thenReturn(responseData);
        // when - action or behaviour that we are going test
        ResultActions response = mockMvc.perform(put("/api/courses/categories/update/{id}", categoryId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(categoryDto)));
        // then - verify the output
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.name", CoreMatchers.is(categoryDto.getName())));
    }
}
