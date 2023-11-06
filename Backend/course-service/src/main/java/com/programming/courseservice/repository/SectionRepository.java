package com.programming.courseservice.repository;

import com.main.progamming.common.repository.BaseRepository;
import com.programming.courseservice.domain.persistent.entity.Section;
import org.springframework.stereotype.Repository;

@Repository
public interface SectionRepository extends BaseRepository<Section> {
}
