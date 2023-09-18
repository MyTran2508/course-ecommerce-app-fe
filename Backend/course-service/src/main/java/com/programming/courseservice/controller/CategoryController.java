package com.programming.courseservice.controller;

import com.main.progamming.common.controller.BaseApiImpl;
import com.main.progamming.common.response.DataResponse;
import com.main.progamming.common.response.ListResponse;
import com.main.progamming.common.service.BaseService;
import com.programming.courseservice.core.dto.CategoryDto;
import com.programming.courseservice.core.persistent.entity.Category;
import com.programming.courseservice.service.CategoryService;
import com.programming.courseservice.util.constant.ShowOpenAPI;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag (
        name = "Course Service - Category Controller",
        description = "Category Controller Exposes Rest APIs for Course-Service"
)
@RestController
@RequestMapping("/api/courses/categories")
@RequiredArgsConstructor
public class CategoryController extends BaseApiImpl<Category, CategoryDto> {
    private final CategoryService categoryService;
    @Override
    protected BaseService<Category, CategoryDto> getBaseService() {
        return categoryService;
    }
    @Override
    @ShowOpenAPI
    public DataResponse<CategoryDto> add(@Valid CategoryDto categoryDto) {
        return super.add(categoryDto);
    }
    @Override
    @ShowOpenAPI
    public DataResponse<CategoryDto> update(@Valid CategoryDto categoryDto, String id) {
        return super.update(categoryDto, id);
    }
    @Override
    @ShowOpenAPI
    public ListResponse<CategoryDto> getAll() {
        return super.getAll();
    }
    @Override
    @ShowOpenAPI
    public DataResponse<CategoryDto> getById(String id) {
        return super.getById(id);
    }
}
