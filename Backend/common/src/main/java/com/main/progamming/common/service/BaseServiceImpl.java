package com.main.progamming.common.service;

import com.main.progamming.common.dto.SearchKeywordDto;
import com.main.progamming.common.error.exception.DataAlreadyExistException;
import com.main.progamming.common.error.exception.ResourceNotFoundException;
import com.main.progamming.common.message.StatusCode;
import com.main.progamming.common.message.StatusMessage;
import com.main.progamming.common.model.BaseMapper;
import com.main.progamming.common.model.BaseModel;
import com.main.progamming.common.repository.BaseRepository;
import com.main.progamming.common.response.DataResponse;
import com.main.progamming.common.response.ListResponse;
import com.main.progamming.common.response.ResponseMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import jakarta.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class BaseServiceImpl<E extends BaseModel, D> implements BaseService<E, D> {
    protected abstract BaseRepository<E> getBaseRepository();
    protected abstract BaseMapper<E, D> getBaseMapper();
    @Override
    @Transactional
    @SuppressWarnings("unchecked")
    public DataResponse<D> create(D dto) {
        try {
            E entity = getBaseMapper().dtoToEntity(dto);
            getBaseRepository().save(entity);
            return ResponseMapper.toDataResponseSuccess(getBaseMapper().entityToDto(entity));
        } catch (DataIntegrityViolationException e) {
            throw new DataAlreadyExistException("Data already exist");
        }


    }

    @Override
    public DataResponse<D> addAll(List<D> listDto) {
        try {
            int count = 0;
            for(D dto: listDto) {
                E newEntity = getBaseMapper().dtoToEntity(dto);
                E oldEntity = newEntity.getId() == null ? null : getBaseRepository().findById(newEntity.getId()).orElse(null);

                if (oldEntity == null) {
                    getBaseRepository().save(newEntity);
                    count++;
                }

                if (oldEntity != null && !oldEntity.equals(newEntity)) {
                    getBaseMapper().dtoToEntity(dto, oldEntity);
                    getBaseRepository().save(oldEntity);
                    count++;
                }
            }
            return ResponseMapper.toDataResponseSuccess(count + " updated successfully!!");
        } catch (Exception e) {
            throw new ResourceNotFoundException(e.getMessage());
        }
    }

    @Override
    @Transactional
    @SuppressWarnings("unchecked")
    public DataResponse<D> update(String id, D dto) {
        Optional<E> optional = getBaseRepository().findById(id);
        if (optional.isEmpty()) {
            throw new ResourceNotFoundException(id + " does not exists in DB");
        }
        E entity = optional.get();
        getBaseMapper().dtoToEntity(dto, entity);
        entity.setId(id);
        getBaseRepository().save(entity);
        return ResponseMapper.toDataResponseSuccess(getBaseMapper().entityToDto(entity));
    }

    @Override
    @Transactional
    @SuppressWarnings("unchecked")
    public DataResponse<D> setRemoved(String id) {
        Optional<E> optional = getBaseRepository().findById(id);
        if (optional.isEmpty()) {
            throw new ResourceNotFoundException(id + " does not exists in DB");
        }
        E entity = optional.get();
        entity.setRemoved(true);
        getBaseRepository().save(entity);
        return ResponseMapper.toDataResponseSuccess(getBaseMapper().entityToDto(entity));
    }

    @Override
    @Transactional
    @SuppressWarnings("unchecked")
    public DataResponse<String> delete(String id) {
        Optional<E> optional = getBaseRepository().findById(id);
        if(optional.isEmpty()) {
            throw new ResourceNotFoundException(id + " does not exists in DB");
        }
        getBaseRepository().deleteById(id);
        return ResponseMapper.toDataResponseSuccess("Delete category id: " + id + " successfully");
    }

    @Override
    @SuppressWarnings("unchecked")
    public DataResponse<D> getById(String id) {
        Optional<E> optional = getBaseRepository().findById(id);
        return optional.map(value -> ResponseMapper.toDataResponseSuccess(getBaseMapper().entityToDto(value)))
                .orElseGet(() -> ResponseMapper.toDataResponse(null, StatusCode.DATA_NOT_FOUND, StatusMessage.DATA_NOT_FOUND));
    }

    @Override
    @SuppressWarnings("unchecked")
    public ListResponse<D> getByIds(String ids) {
        String[] arr = ids.trim().split(",");
        if (arr.length > 0) {
            List<String> listIds = Arrays.asList(arr);
            return ResponseMapper.toListResponseSuccess(getBaseRepository().findAllById(listIds)
                    .stream().map(value -> getBaseMapper().entityToDto(value)).collect(Collectors.toList()));
        }
        return ResponseMapper.toListResponseSuccess(null);
    }

    @Override
    @SuppressWarnings("unchecked")
    public ListResponse<D> getAll() {
        return ResponseMapper.toListResponseSuccess(
                getBaseRepository().findAll()
                        .stream().map(value -> getBaseMapper().entityToDto(value)).collect(Collectors.toList()));
    }


    @Override
    @SuppressWarnings("unchecked")
    public ListResponse<D> getAllByKeyword(String keyword) {
        return ResponseMapper.toListResponseSuccess(getListSearchResults(keyword));
    }

    @Override
    @SuppressWarnings("unchecked")
    public ListResponse<D> searchByKeyword(SearchKeywordDto searchKeywordDto) {
        Pageable pageable = PageRequest.of(searchKeywordDto.getPageIndex(), searchKeywordDto.getPageSize());
        return ResponseMapper.toPagingResponseSuccess(getPageResults(searchKeywordDto, pageable));
    }

    protected abstract Page<D> getPageResults(SearchKeywordDto searchKeywordDto, Pageable pageable);

    protected abstract List<D> getListSearchResults(String keyword);
}
