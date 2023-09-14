package com.main.progamming.common.service;

import com.main.progamming.common.dto.ChangeStatusDto;
import com.main.progamming.common.dto.SearchKeywordDto;
import com.main.progamming.common.response.DataResponse;
import com.main.progamming.common.response.ListResponse;

public interface BaseService<E, D> {
    DataResponse<E> create(D dto);

    DataResponse<E> update(String id, D dto);

    DataResponse<E> delete(String id);

    DataResponse<E> getById(String id);

    ListResponse<E> getByIds(String ids);

    ListResponse<E> getAll();

    ListResponse<E> getAllByKeyword(String keyword);

    ListResponse<E> searchByKeyword(SearchKeywordDto searchKeywordDto);
}
