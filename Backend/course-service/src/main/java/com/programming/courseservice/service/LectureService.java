package com.programming.courseservice.service;

import com.main.progamming.common.dto.SearchKeywordDto;
import com.main.progamming.common.error.exception.DataNotFoundException;
import com.main.progamming.common.message.StatusMessage;
import com.main.progamming.common.model.BaseMapper;
import com.main.progamming.common.repository.BaseRepository;
import com.main.progamming.common.response.DataResponse;
import com.main.progamming.common.response.ResponseMapper;
import com.main.progamming.common.service.BaseServiceImpl;
import com.programming.courseservice.domain.dto.LectureDto;
import com.programming.courseservice.domain.mapper.LectureMapper;
import com.programming.courseservice.domain.persistent.entity.Lecture;
import com.programming.courseservice.domain.persistent.entity.Section;
import com.programming.courseservice.domain.persistent.enumrate.LectureType;
import com.programming.courseservice.repository.LectureRepository;
import com.programming.courseservice.repository.SectionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LectureService extends BaseServiceImpl<Lecture, LectureDto> {
    private final LectureRepository lectureRepository;

    private final LectureMapper lectureMapper;

    private final SectionRepository sectionRepository;

    @Override
    protected BaseRepository<Lecture> getBaseRepository() {
        return lectureRepository;
    }

    @Override
    protected BaseMapper<Lecture, LectureDto> getBaseMapper() {
        return lectureMapper;
    }

    @Override
    protected Page<LectureDto> getPageResults(SearchKeywordDto searchKeywordDto, Pageable pageable) {
        return null;
    }

    @Override
    protected List<LectureDto> getListSearchResults(String keyword) {
        return null;
    }

    /** update or insert lecture by section id */
    public DataResponse<String> insert(String sectionId, LectureDto lectureDto) {
        Section section = sectionRepository.findById(sectionId).orElse(null);

        if (section != null) {

            // save lecture to section
            Lecture lecture = lectureMapper.dtoToEntity(lectureDto);
            section.getLectures().add(lecture);

            // update total duration video lectures
            if (lectureDto.getLectureType() == LectureType.VIDEO) {
                Long totalDurationVideo = section.getTotalDurationVideoLectures() + lecture.getVideoDuration();
                section.setTotalDurationVideoLectures(totalDurationVideo);
            }

            // save section
            sectionRepository.save(section);

            return ResponseMapper.toDataResponseSuccess(StatusMessage.REQUEST_SUCCESS);
        } else {

            // throw exception if section is not found
           throw new DataNotFoundException(StatusMessage.DATA_NOT_FOUND);
        }
    }

    // update total duration video lectures for update
    public void updateTotalDurationVideoLecturesForUpdate(LectureDto lectureDto, String lectureId) {
        Lecture lecture = lectureRepository.findById(lectureId).orElse(null);

        if (lecture != null && lecture.getLectureType() == LectureType.VIDEO && lecture.getVideoDuration() != lectureDto.getVideoDuration()) {
            // get section from lecture id
            String sectionId = lectureRepository.getSectionIdByLectureId(lectureId);
            System.out.println(sectionId);
            Section section = sectionRepository.findById(sectionId).orElse(null);

            // update total duration video lectures
            Long totalDurationVideo = section.getTotalDurationVideoLectures() - lecture.getVideoDuration() + lectureDto.getVideoDuration();
            System.out.println(totalDurationVideo);
            section.setTotalDurationVideoLectures(totalDurationVideo);

            // save section
            sectionRepository.save(section);
        }
    }

    /** Update list of lecture by section id */
    public DataResponse<String> updateList(String sectionId, List<LectureDto> lectureDtos) {
        Section section = sectionRepository.findById(sectionId).orElse(null);

        // Convert list dto to list entity
        List<Lecture> listLecture = new ArrayList<>();
        Long totalDurationVideo = 0L;
        for (LectureDto lectureDto : lectureDtos) {
            listLecture.add(lectureMapper.dtoToEntity(lectureDto));

            if (lectureDto.getLectureType() == LectureType.VIDEO) {
                totalDurationVideo += lectureDto.getVideoDuration();
            }
        }

        section.setTotalDurationVideoLectures(totalDurationVideo);
        // save list if section is not null
        if (section != null) {
            section.setLectures(listLecture);
            sectionRepository.save(section);
            return ResponseMapper.toDataResponseSuccess(StatusMessage.REQUEST_SUCCESS);

        } else {
            // throw exception if section is not found
            throw new DataNotFoundException(StatusMessage.DATA_NOT_FOUND);
        }
    }
}
