package com.main.progamming.common.controller;

import com.main.progamming.common.dto.ChangeStatusDto;
import com.main.progamming.common.dto.SearchKeywordDto;
import com.main.progamming.common.response.DataResponse;
import com.main.progamming.common.response.ListResponse;
import com.main.progamming.common.util.ApiResources;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;


public interface BaseApi<E,D> {
    @PostMapping(ApiResources.ADD)
    public DataResponse<E> add(@Valid @RequestBody D objectDTO);

    @PutMapping(ApiResources.UPDATE)
    public DataResponse<E> update(@Valid @RequestBody D objectDTO, @PathVariable("id") String id);

    @GetMapping(ApiResources.GET_BY_ID)
    public DataResponse<E> getById(@RequestParam String id);

    @PutMapping(ApiResources.DELETE)
    public DataResponse<E> delete(@PathVariable("id") String id);

    @GetMapping(ApiResources.GET_ALL)
    public ListResponse<E> getAll();

    @GetMapping(ApiResources.GET_BY_IDS)
    public ListResponse<E> getByIds(@RequestParam String ids);

    @GetMapping(ApiResources.GET_ALL_BY_KEYWORD)
    public ListResponse<E> getAllByKeyword(@RequestParam String keyword);

    @PostMapping(ApiResources.SEARCH_BY_KEYWORD)
    public ListResponse<E> searchByKeyword(@Valid @RequestBody SearchKeywordDto searchKeywordDto);
}
