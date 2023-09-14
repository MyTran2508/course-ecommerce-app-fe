package com.main.progamming.common.controller;

import com.main.progamming.common.dto.ChangeStatusDto;
import com.main.progamming.common.dto.SearchKeywordDto;
import com.main.progamming.common.response.DataResponse;
import com.main.progamming.common.response.ListResponse;
import com.main.progamming.common.service.BaseService;
import com.main.progamming.common.util.ApiResources;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;


public abstract class BaseApiImpl<E, D> implements BaseApi<E, D> {
    protected abstract BaseService<E, D> getBaseService();

    @Override
    @PostMapping(ApiResources.ADD)
    public DataResponse<E> add(D objectDTO) {
        return this.getBaseService().create(objectDTO);
    }

    @Override
    @PutMapping(ApiResources.UPDATE)
    public DataResponse<E> update(@Valid @RequestBody D objectDTO, @PathVariable("id") String id) {
        return this.getBaseService().update(id, objectDTO);
    }

    @Override
    @GetMapping(ApiResources.GET_BY_ID)
    public DataResponse<E> getById(@RequestParam String id) {
        return this.getBaseService().getById(id);
    }

    @Override
    @PutMapping(ApiResources.DELETE)
    public DataResponse<E> delete(@PathVariable("id") String id) {
        return this.getBaseService().delete(id);
    }

    @Override
    @GetMapping(ApiResources.GET_ALL)
    public ListResponse<E> getAll() {
        return this.getBaseService().getAll();
    }

    @Override
    @GetMapping(ApiResources.GET_BY_IDS)
    public ListResponse<E> getByIds(@RequestParam String ids) {
        return this.getBaseService().getByIds(ids);
    }

    @Override
    @GetMapping(ApiResources.GET_ALL_BY_KEYWORD)
    public ListResponse<E> getAllByKeyword(@RequestParam String keyword) {
        return this.getBaseService().getAllByKeyword(keyword);
    }

    @Override
    @PostMapping(ApiResources.SEARCH_BY_KEYWORD)
    public ListResponse<E> searchByKeyword(@Valid @RequestBody SearchKeywordDto searchKeywordDto) {
        return this.getBaseService().searchByKeyword(searchKeywordDto);
    }
}
