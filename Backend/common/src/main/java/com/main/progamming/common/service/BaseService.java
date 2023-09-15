package com.main.progamming.common.service;

import com.main.progamming.common.dto.ChangeStatusDto;
import com.main.progamming.common.dto.SearchKeywordDto;
import com.main.progamming.common.response.DataResponse;
import com.main.progamming.common.response.ListResponse;

public interface BaseService<E, D> {
    DataResponse<D> create(D dto);

    DataResponse<D> update(String id, D dto);

    DataResponse<D> delete(String id);

    DataResponse<D> getById(String id);

    ListResponse<D> getByIds(String ids);

    ListResponse<D> getAll();

    ListResponse<D> getAllByKeyword(String keyword);

    ListResponse<D> searchByKeyword(SearchKeywordDto searchKeywordDto);
}
