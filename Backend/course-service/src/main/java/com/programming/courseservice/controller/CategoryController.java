package com.programming.courseservice.controller;

import com.main.progamming.common.controller.BaseApiImpl;
import com.main.progamming.common.response.DataResponse;
import com.main.progamming.common.response.ListResponse;
import com.main.progamming.common.service.BaseService;
import com.programming.courseservice.communication.WebClient.FetchData;
import com.programming.courseservice.core.dto.CategoryDto;
import com.programming.courseservice.core.persistent.entity.Category;
import com.programming.courseservice.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CategoryController extends BaseApiImpl<Category, CategoryDto> {
    private final CategoryService categoryService;
    private final FetchData webClient;
    @Override
    protected BaseService<Category, CategoryDto> getBaseService() {
        return categoryService;
    }

    @Override
    public DataResponse<CategoryDto> add(CategoryDto objectDTO) {
        return super.add(objectDTO);
    }

    @Override
    public ListResponse<CategoryDto> getAll() {
        return super.getAll();
    }
}
