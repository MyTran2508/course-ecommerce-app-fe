package com.main.ocean.common.service;

import com.main.ocean.common.dto.ChangeStatusDto;
import com.main.ocean.common.dto.SearchKeywordDto;
import com.main.ocean.common.response.DataResponse;
import com.main.ocean.common.response.ListResponse;

public interface BaseService<E, D> {
    DataResponse<E> create(D dto);

    DataResponse<E> update(String id, D dto);

    DataResponse<E> delete(String id);

    DataResponse<E> getById(String id);

    ListResponse<E> getByIds(String ids);

    ListResponse<E> getAll();

    DataResponse<E> changeStatus(ChangeStatusDto changeStatusDto);

    ListResponse<E> getAllByKeyword(String keyword);

    ListResponse<E> searchByKeyword(SearchKeywordDto searchKeywordDto);
}
