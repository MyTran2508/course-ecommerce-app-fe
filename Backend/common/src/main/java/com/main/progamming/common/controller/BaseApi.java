package com.main.progamming.common.controller;

import com.main.progamming.common.dto.ChangeStatusDto;
import com.main.progamming.common.dto.SearchKeywordDto;
import com.main.progamming.common.response.DataResponse;
import com.main.progamming.common.response.ListResponse;
import com.main.progamming.common.util.ApiResources;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;


public interface BaseApi<E,D> {
    @PostMapping(ApiResources.ADD)
    public DataResponse<D> add(@Valid @RequestBody D objectDTO);
    @PostMapping(ApiResources.ADD_ALL)
    public DataResponse<D> addAll(@RequestBody List<D> listDto);
    @PutMapping(ApiResources.UPDATE)
    public DataResponse<D> update(@Valid @RequestBody D objectDTO, @PathVariable("id") String id);

    @GetMapping(ApiResources.GET_BY_ID)
    public DataResponse<D> getById(@RequestParam String id);

    @PutMapping(ApiResources.REMOVED)
    public DataResponse<D> setRemoved(@PathVariable("id") String id);

    @PutMapping(ApiResources.DELETE)
    public DataResponse<String> delete(@PathVariable("id") String id);

    @GetMapping(ApiResources.GET_ALL)
    public ListResponse<D> getAll();

    @GetMapping(ApiResources.GET_BY_IDS)
    public ListResponse<D> getByIds(@RequestParam String ids);

    @GetMapping(ApiResources.GET_ALL_BY_KEYWORD)
    public ListResponse<D> getAllByKeyword(@RequestParam String keyword);

    @PostMapping(ApiResources.SEARCH_BY_KEYWORD)
    public ListResponse<D> searchByKeyword(@Valid @RequestBody SearchKeywordDto searchKeywordDto);
}
