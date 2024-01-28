package com.programming.courseservice.service;

import com.main.progamming.common.dto.SearchKeywordDto;
import com.main.progamming.common.model.BaseMapper;
import com.main.progamming.common.repository.BaseRepository;
import com.main.progamming.common.service.BaseServiceImpl;
import com.programming.courseservice.domain.dto.TopicDto;
import com.programming.courseservice.domain.mapper.TopicMapper;
import com.programming.courseservice.domain.persistent.entity.Topic;
import com.programming.courseservice.repository.TopicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TopicService extends BaseServiceImpl<Topic, TopicDto> {
    private final TopicRepository topicRepository;

    private final TopicMapper topicMapper;

    @Override
    protected BaseRepository<Topic> getBaseRepository() {
        return topicRepository;
    }

    @Override
    protected BaseMapper<Topic, TopicDto> getBaseMapper() {
        return topicMapper;
    }

    @Override
    protected Page<TopicDto> getPageResults(SearchKeywordDto searchKeywordDto, Pageable pageable) {
        return null;
    }

    @Override
    protected List<TopicDto> getListSearchResults(String keyword) {
        return null;
    }
}
