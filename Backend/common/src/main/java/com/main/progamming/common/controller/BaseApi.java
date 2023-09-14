package com.main.ocean.common.controller;


import com.main.ocean.common.dto.SearchKeywordDto;
import com.main.ocean.common.response.DataResponse;
import com.main.ocean.common.response.ListResponse;
import com.main.ocean.common.util.ApiResources;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface BaseApi<E, Q, R> {


    @PostMapping(ApiResources.ADD)
    DataResponse<R> add(@RequestBody Q objectDTO);


    @PostMapping(ApiResources.ADD_ALL)
    DataResponse<R> addAll(@RequestBody List<Q> objectDTO);

    @PutMapping(ApiResources.UPDATE)
    DataResponse<R> update(@RequestBody Q objectDTO,
                           @PathVariable("key") String key);

    @GetMapping(ApiResources.GET_BY_ID)
    public DataResponse<R> getById(@RequestParam String id);

    @PutMapping(ApiResources.DELETE)
    public DataResponse<R> delete(@PathVariable("id") String id);

    @GetMapping(ApiResources.GET_ALL)
    public ListResponse<R> getAll();


    @GetMapping(ApiResources.GET_BY_IDS)
    public ListResponse<R> getByIds(@RequestParam String ids);

    @GetMapping(ApiResources.GET_ALL_BY_KEYWORD)
    public ListResponse<E> getAllByKeyword(@RequestParam String keyword);

    @PostMapping(ApiResources.SEARCH_BY_KEYWORD)
    public ListResponse<R> searchByKeyword(@Valid @RequestBody SearchKeywordDto searchKeywordDto);

    @GetMapping(ApiResources.GET_ALL_BY_PAGE)
    public ListResponse<R> getAllByPage(@RequestParam("page") int page, @RequestParam("size") int size);
}
