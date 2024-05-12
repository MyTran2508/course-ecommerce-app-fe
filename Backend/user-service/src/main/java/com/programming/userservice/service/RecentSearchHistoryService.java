package com.programming.userservice.service;

import com.main.progamming.common.error.exception.DataNotFoundException;
import com.main.progamming.common.response.DataResponse;
import com.main.progamming.common.response.ListResponse;
import com.main.progamming.common.response.ResponseMapper;
import com.programming.userservice.domain.dto.RecentSearchHistoryDto;
import com.programming.userservice.domain.mapper.RecentSearchHistoryMapper;
import com.programming.userservice.domain.persistent.entity.RecentSearchHistory;
import com.programming.userservice.domain.persistent.enumrate.ModuleSearch;
import com.programming.userservice.repository.RecentSearchHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RecentSearchHistoryService {

    private final RecentSearchHistoryRepository recentSearchHistoryRepository;

    private final RecentSearchHistoryMapper recentSearchHistoryMapper;

    public ListResponse<RecentSearchHistoryDto> getByUserNameAndModuleName(String username, ModuleSearch moduleSearch) {

        List<RecentSearchHistory> entities =
                recentSearchHistoryRepository.findByUsernameAndModuleSearch(username, moduleSearch);

        if (entities.isEmpty()) {
            throw new DataNotFoundException("Data not found");
        }

        List<RecentSearchHistoryDto> recentSearchHistoryDtoList = entities.stream()
                .map(recentSearchHistoryMapper::entityToDto)
                .sorted(Comparator.comparing(RecentSearchHistoryDto::getCreated).reversed())
                .limit(5)
                .toList();

        return ResponseMapper.toListResponseSuccess(recentSearchHistoryDtoList);
    }

    public DataResponse<RecentSearchHistoryDto> create(RecentSearchHistoryDto recentSearchHistoryDto) {

        RecentSearchHistory entity = recentSearchHistoryMapper.dtoToEntity(recentSearchHistoryDto);

        RecentSearchHistory alreadyExistEntiy =
                recentSearchHistoryRepository.findByUsernameAndModuleSearchAndKeywordAndKeywordType(
                        entity.getUsername(),
                        entity.getModuleSearch(),
                        entity.getKeyword(),
                        entity.getKeywordType()
                );

        if (alreadyExistEntiy == null) {
            RecentSearchHistory savedEntity = recentSearchHistoryRepository.save(entity);

            RecentSearchHistoryDto savedDto = recentSearchHistoryMapper.entityToDto(savedEntity);

            return ResponseMapper.toDataResponseSuccess(savedDto);
        } else {
            alreadyExistEntiy.setCountHistory(alreadyExistEntiy.getCountHistory() + 1);

            RecentSearchHistory updatedEntity = recentSearchHistoryRepository.save(alreadyExistEntiy);

            RecentSearchHistoryDto savedDto = recentSearchHistoryMapper.entityToDto(updatedEntity);

            return ResponseMapper.toDataResponseSuccess(savedDto);
        }
    }

    public DataResponse<RecentSearchHistoryDto> update(String id, RecentSearchHistoryDto recentSearchHistoryDto) {

        RecentSearchHistory recentSearchHistory = recentSearchHistoryRepository.findById(id).get();

        if (recentSearchHistory == null) {
            throw new DataNotFoundException("Data not found");
        }

        recentSearchHistoryMapper.dtoToEntity(recentSearchHistoryDto, recentSearchHistory);

        recentSearchHistory.setId(id);

        RecentSearchHistory updatedEntity = recentSearchHistoryRepository.save(recentSearchHistory);

        RecentSearchHistoryDto responseDto = recentSearchHistoryMapper.entityToDto(updatedEntity);

        return ResponseMapper.toDataResponseSuccess(responseDto);
    }

    public DataResponse<String> deleteByUsernameAndModuleSearch(String username, ModuleSearch moduleSearch) {

        List<RecentSearchHistory> deleteEntities =
                recentSearchHistoryRepository.findByUsernameAndModuleSearch(username, moduleSearch);

        if (deleteEntities.isEmpty()) {
            throw new DataNotFoundException("Data not found");
        }

        recentSearchHistoryRepository.deleteAll(deleteEntities);
        return ResponseMapper.toDataResponseSuccess("Data deleted successfully");
    }
}
