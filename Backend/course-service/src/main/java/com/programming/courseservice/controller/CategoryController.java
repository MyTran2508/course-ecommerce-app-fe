package com.programming.courseservice.controller;

import com.main.progamming.common.controller.BaseApiImpl;
import com.main.progamming.common.response.DataResponse;
import com.main.progamming.common.response.ListResponse;
import com.main.progamming.common.service.BaseService;
import com.programming.courseservice.domain.dto.CategoryDto;
import com.programming.courseservice.domain.persistent.entity.Category;
import com.programming.courseservice.service.CategoryService;
import com.programming.courseservice.util.annotation.ShowOpenAPI;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public DataResponse<CategoryDto> add(@Valid CategoryDto categoryDto) {
        System.out.println("Vao day");
        return super.add(categoryDto);
    }

    @Override
    @ShowOpenAPI
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public DataResponse<CategoryDto> update(@Valid CategoryDto categoryDto, String id) {
        System.out.println("Vao day");
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

    @ShowOpenAPI
    @GetMapping("/get-by-name/{name}")
    public DataResponse<CategoryDto> getByName(@PathVariable String name) {
        return categoryService.getByName(name);
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public DataResponse<CategoryDto> setRemoved(String id) {
        return super.setRemoved(id);
    }
}