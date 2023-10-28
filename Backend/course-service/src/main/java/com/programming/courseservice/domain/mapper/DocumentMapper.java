package com.programming.courseservice.domain.mapper;

import com.main.progamming.common.model.BaseMapperImpl;
import com.programming.courseservice.domain.dto.DocumentDto;
import com.programming.courseservice.domain.persistent.entity.Document;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class DocumentMapper extends BaseMapperImpl<Document, DocumentDto> {
    public DocumentMapper(ModelMapper modelMapper) {
        super(modelMapper);
    }

    @Override
    protected Class<Document> getEntityClass() {
        return Document.class;
    }

    @Override
    protected Class<DocumentDto> getDtoClass() {
        return DocumentDto.class;
    }
}
