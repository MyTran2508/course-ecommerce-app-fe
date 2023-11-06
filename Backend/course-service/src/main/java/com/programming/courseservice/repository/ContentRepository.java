package com.programming.courseservice.repository;

import com.main.progamming.common.repository.BaseRepository;
import com.programming.courseservice.domain.persistent.entity.Content;
import org.springframework.stereotype.Repository;

@Repository
public interface ContentRepository extends BaseRepository<Content> {
}
