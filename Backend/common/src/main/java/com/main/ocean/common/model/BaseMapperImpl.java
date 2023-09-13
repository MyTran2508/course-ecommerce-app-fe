package com.main.ocean.common.model;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BaseMapperImpl<E, D> implements BaseMapper<E, D> {
    protected ModelMapper modelMapper;

    @Autowired
    public BaseMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        this.modelMapper.getConfiguration().setAmbiguityIgnored(true);
    }
}
