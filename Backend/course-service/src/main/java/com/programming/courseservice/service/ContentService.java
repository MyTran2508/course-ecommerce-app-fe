package com.programming.courseservice.service;

import com.main.progamming.common.dto.SearchKeywordDto;
import com.main.progamming.common.error.exception.DataNotFoundException;
import com.main.progamming.common.message.StatusCode;
import com.main.progamming.common.message.StatusMessage;
import com.main.progamming.common.model.BaseMapper;
import com.main.progamming.common.repository.BaseRepository;
import com.main.progamming.common.response.DataResponse;
import com.main.progamming.common.response.ResponseMapper;
import com.main.progamming.common.service.BaseServiceImpl;
import com.programming.courseservice.domain.dto.ContentDto;
import com.programming.courseservice.domain.mapper.ContentMapper;
import com.programming.courseservice.domain.persistent.entity.Content;
import com.programming.courseservice.repository.ContentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContentService extends BaseServiceImpl<Content, ContentDto> {
    private final ContentRepository contentRepository;
    private final ContentMapper contentMapper;
    @Override
    protected BaseRepository<Content> getBaseRepository() {
        return contentRepository;
    }

    @Override
    protected BaseMapper<Content, ContentDto> getBaseMapper() {
        return contentMapper;
    }

    @Override
    protected Page<ContentDto> getPageResults(SearchKeywordDto searchKeywordDto, Pageable pageable) {
        return null;
    }

    @Override
    protected List<ContentDto> getListSearchResults(String keyword) {
        return null;
    }

    public DataResponse<ContentDto> getByCourseId(String id) {
        Content content = contentRepository.findByCourseId(id);

        if(content == null) {
            throw new DataNotFoundException(id + " doesn't exists in db");
        }

        return ResponseMapper.toDataResponseSuccess(contentMapper.entityToDto(content));
    }
}
