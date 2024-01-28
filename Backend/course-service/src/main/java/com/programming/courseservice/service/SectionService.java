package com.programming.courseservice.service;

import com.main.progamming.common.dto.SearchKeywordDto;
import com.main.progamming.common.message.StatusCode;
import com.main.progamming.common.message.StatusMessage;
import com.main.progamming.common.model.BaseMapper;
import com.main.progamming.common.repository.BaseRepository;
import com.main.progamming.common.response.DataResponse;
import com.main.progamming.common.response.ResponseMapper;
import com.main.progamming.common.service.BaseServiceImpl;
import com.programming.courseservice.domain.dto.LectureDto;
import com.programming.courseservice.domain.dto.SectionDto;
import com.programming.courseservice.domain.mapper.SectionMapper;
import com.programming.courseservice.domain.persistent.entity.Section;
import com.programming.courseservice.repository.LectureRepository;
import com.programming.courseservice.repository.SectionRepository;
import com.programming.courseservice.utilities.FileUtils;
import com.programming.courseservice.utilities.constant.CourseConstrant;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SectionService extends BaseServiceImpl<Section, SectionDto> {
    private final SectionRepository sectionRepository;
    private final LectureRepository lectureRepository;
    private final SectionMapper sectionMapper;
    private final StorageS3Service storageS3Service;
    private final StorageService storageService;
    private final FileUtils fileUtils;
    @Override
    protected BaseRepository<Section> getBaseRepository() {
        return sectionRepository;
    }

    @Override
    protected BaseMapper<Section, SectionDto> getBaseMapper() {
        return sectionMapper;
    }

    @Override
    protected Page<SectionDto> getPageResults(SearchKeywordDto searchKeywordDto, Pageable pageable) {
        return null;
    }

    @Override
    protected List<SectionDto> getListSearchResults(String keyword) {
        return null;
    }

    public DataResponse<List<String>> uploadFileSection(MultipartFile[] files) {
        List<String> listPath = new LinkedList<>();
        List<String> listError = new ArrayList<>();
        Integer idx = 0;
        for (MultipartFile file: files) {
            if(fileUtils.isFileVideo(file)) {
                listPath.add(storageS3Service.uploadFile(CourseConstrant.S3Constrant.PATH_COURSE_LECTURE, file));
            } else if (fileUtils.isFileDocument(file)){
                listPath.add(storageS3Service.uploadFile(CourseConstrant.S3Constrant.PATH_COURSE_DOCUMENT, file));
            } else {
                listError.add(idx.toString());
            }
            idx++;
        }
        if(listError.size() > 0) {
            return ResponseMapper.toDataResponse(listError, StatusCode.DATA_NOT_MAP, StatusMessage.DATA_NOT_MAP);
        }
        return ResponseMapper.toDataResponseSuccess(listPath);
    }

    public ResponseEntity<ByteArrayResource> loadFile(String path) {
        byte[] data = storageS3Service.downloadFile(path);
        ByteArrayResource resource = new ByteArrayResource(data);
        return ResponseEntity.ok()
                .contentLength(data.length)
                .header("Content-type", "application/octet-stream")
                .header("Content-disposition", "attachment; fileName=\"" + path + "\"")
                .body(resource);
    }

    public SectionDto updateSection(SectionDto sectionDto) {
        for (LectureDto lectureDto: sectionDto.getLectures()) {
            if(lectureDto.getOrdinalNumber() < 1) {
                storageS3Service.deleteFile(lectureDto.getUrl());
                lectureRepository.deleteById(lectureDto.getId());
                sectionDto.getLectures().remove(lectureDto);
            }
        }
        return sectionDto;
    }
}
