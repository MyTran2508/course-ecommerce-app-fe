package com.programming.courseservice.service;

import com.main.progamming.common.dto.SearchKeywordDto;
import com.main.progamming.common.model.BaseMapper;
import com.main.progamming.common.repository.BaseRepository;
import com.main.progamming.common.service.BaseServiceImpl;
import com.programming.courseservice.domain.dto.DocumentDto;
import com.programming.courseservice.domain.mapper.DocumentMapper;
import com.programming.courseservice.domain.persistent.entity.Document;
import com.programming.courseservice.repository.DocumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DocumentService extends BaseServiceImpl<Document, DocumentDto> {
    private DocumentRepository documentRepository;
    private DocumentMapper documentMapper;
    @Override
    protected BaseRepository<Document> getBaseRepository() {
        return documentRepository;
    }

    @Override
    protected BaseMapper<Document, DocumentDto> getBaseMapper() {
        return documentMapper;
    }

    @Override
    protected Page<DocumentDto> getPageResults(SearchKeywordDto searchKeywordDto, Pageable pageable) {
        return null;
    }

    @Override
    protected List<DocumentDto> getListSearchResults(String keyword) {
        return null;
    }
}
