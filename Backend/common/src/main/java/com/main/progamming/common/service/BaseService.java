package com.main.progamming.common.service;

import com.main.progamming.common.dto.ChangeStatusDto;
import com.main.progamming.common.dto.SearchKeywordDto;
import com.main.progamming.common.response.DataResponse;
import com.main.progamming.common.response.ListResponse;

import java.util.List;

public interface BaseService<E, D> {
    DataResponse<String> create(D dto);

    DataResponse<D> addAll(List<D> listDto);

    DataResponse<D> update(String id, D dto);

    DataResponse<D> setRemoved(String id);

    DataResponse<String> delete(String id);

    DataResponse<D> getById(String id);

    ListResponse<D> getByIds(String ids);

    ListResponse<D> getAll();

    ListResponse<D> getAllByKeyword(String keyword);

    ListResponse<D> searchByKeyword(SearchKeywordDto searchKeywordDto);
}
