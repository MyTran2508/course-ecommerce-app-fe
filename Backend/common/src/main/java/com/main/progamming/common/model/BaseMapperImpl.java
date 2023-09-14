package com.main.progamming.common.model;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


public abstract class BaseMapperImpl<E, D> implements BaseMapper<E, D> {
    protected ModelMapper modelMapper;
    @Autowired
    public BaseMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        this.modelMapper.getConfiguration().setAmbiguityIgnored(true);
    }
    @Override
    public D entityToDto(E entity) {
        return modelMapper.map(entity, getDtoClass());
    }

    @Override
    public E dtoToEntity(D dto) {
        return modelMapper.map(dto, getEntityClass());
    }

    @Override
    public void entityToDto(E entity, D dto) {
        modelMapper.map(entity, dto);
    }
    @Override
    public void dtoToEntity(D dto, E entity) {
        modelMapper.map(dto, entity);
    }

    protected abstract Class<E> getEntityClass();

    protected abstract Class<D> getDtoClass();
}
