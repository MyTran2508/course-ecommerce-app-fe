package com.main.ocean.common.controller;

import com.main.ocean.common.dto.SearchKeywordDto;
import com.main.ocean.common.response.DataResponse;
import com.main.ocean.common.response.ListResponse;
import com.main.ocean.common.service.BaseService;
import com.main.ocean.common.util.ApiResources;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;


public abstract class BaseApiImpl<E, Q, R> implements BaseApi<E, Q, R> {
    protected abstract BaseService<E, Q, R> getBaseService();

    @Override
    @PostMapping(ApiResources.ADD)
    public DataResponse<R> add(Q dto) {
        return this.getBaseService().create(dto);
    }

    @Override
    @PutMapping(ApiResources.UPDATE)
    public DataResponse<R> update(@Valid @RequestBody Q dto, @PathVariable("id") String id) {
        return this.getBaseService().update(id, dto);
    }

    @Override
    @GetMapping(ApiResources.GET_BY_ID)
    public DataResponse<R> getById(@RequestParam String id) {
        return this.getBaseService().getById(id);
    }

    @Override
    @PutMapping(ApiResources.DELETE)
    public DataResponse<R> delete(@PathVariable("id") String id) {
        return this.getBaseService().delete(id);
    }

    @Override
    @GetMapping(ApiResources.GET_ALL)
    public ListResponse<R> getAll() {
        return this.getBaseService().getAll();
    }

    @Override
    @GetMapping(ApiResources.GET_BY_IDS)
    public ListResponse<R> getByIds(@RequestParam String ids) {
        return this.getBaseService().getByIds(ids);
    }

    @Override
    @GetMapping(ApiResources.GET_ALL_BY_KEYWORD)
    public ListResponse<R> getAllByKeyword(@RequestParam String keyword) {
        return this.getBaseService().getAllByKeyword(keyword);
    }

    @Override
    @PostMapping(ApiResources.SEARCH_BY_KEYWORD)
    public ListResponse<R> searchByKeyword(@Valid @RequestBody SearchKeywordDto searchKeywordDto) {
        return this.getBaseService().searchByKeyword(searchKeywordDto);
    }
}
