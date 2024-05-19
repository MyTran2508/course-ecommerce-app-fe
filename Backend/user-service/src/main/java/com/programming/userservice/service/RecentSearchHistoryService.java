package com.programming.userservice.service;

import com.main.progamming.common.dto.SearchConditionDto;
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

import java.util.ArrayList;
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
                .map(entity -> {
                    List<SearchConditionDto> searchChooseList = new ArrayList<>();
                    List<SearchConditionDto> searchKeywordDtoList = new ArrayList<>();

                    String[] searchChooseArray =
                            entity.getSearchChoose() == null ? new String[0] : entity.getSearchChoose().split("//n");

                    String[] searchKeywordArray =
                            entity.getSearchKeyword() == null ? new String[0] : entity.getSearchKeyword().split("//n");

                    for (String itemSearchChoose: searchChooseArray) {
                        SearchConditionDto searchConditionDto = new SearchConditionDto();
                        searchConditionDto.setKeywordType(Integer.valueOf(itemSearchChoose.split("//b")[0]));
                        searchConditionDto.setKeyword(itemSearchChoose.split("//b")[1]);
                        searchChooseList.add(searchConditionDto);
                    }
                    for (String itemSearchKeyword: searchKeywordArray) {
                        System.out.println(itemSearchKeyword);
                        SearchConditionDto searchConditionDto = new SearchConditionDto();
                        searchConditionDto.setKeywordType(Integer.valueOf(itemSearchKeyword.split("//b")[0]));
                        searchConditionDto.setKeyword(itemSearchKeyword.split("//b")[1]);
                        searchKeywordDtoList.add(searchConditionDto);
                    }

                    RecentSearchHistoryDto responseDto = recentSearchHistoryMapper.entityToDto(entity);
                    responseDto.setSearchChooseList(searchChooseList);
                    responseDto.setSearchKeywordDtoList(searchKeywordDtoList);
                    return responseDto;
                })
                .sorted(Comparator.comparing(RecentSearchHistoryDto::getCreated).reversed())
                .limit(5)
                .toList();

        return ResponseMapper.toListResponseSuccess(recentSearchHistoryDtoList);
    }

    public DataResponse<RecentSearchHistoryDto> create(RecentSearchHistoryDto recentSearchHistoryDto) {

        RecentSearchHistory entity = recentSearchHistoryMapper.dtoToEntity(recentSearchHistoryDto);

        StringBuilder searchChoose = new StringBuilder("");
        StringBuilder searchKeyword = new StringBuilder("");

        for (SearchConditionDto searchConditionDto : recentSearchHistoryDto.getSearchChooseList()) {

            searchChoose.append(searchConditionDto.getKeywordType()).append("//b")
                    .append(searchConditionDto.getKeyword().trim()).append("//n");
        }

        for (SearchConditionDto searchConditionDto : recentSearchHistoryDto.getSearchKeywordDtoList()) {

            searchKeyword.append(searchConditionDto.getKeywordType()).append("//b")
                    .append(searchConditionDto.getKeyword()).append("//n");
        }

        RecentSearchHistory alreadyExistEntiy =
                recentSearchHistoryRepository.findByUsernameAndModuleSearchAndSearchChooseAndSearchKeyword(
                        entity.getUsername(),
                        entity.getModuleSearch(),
                        searchChoose.toString(),
                        searchKeyword.toString()
                );

        entity.setSearchChoose(searchChoose.isEmpty() ? null : searchChoose.toString());
        entity.setSearchKeyword(searchKeyword.isEmpty() ? null : searchKeyword.toString());

        if (alreadyExistEntiy == null) {
            RecentSearchHistory savedEntity = recentSearchHistoryRepository.save(entity);

            recentSearchHistoryDto.setId(savedEntity.getId());
            return ResponseMapper.toDataResponseSuccess(recentSearchHistoryDto);
        } else {
            alreadyExistEntiy.setCountHistory(alreadyExistEntiy.getCountHistory() + 1);

            RecentSearchHistory updatedEntity = recentSearchHistoryRepository.save(alreadyExistEntiy);

            recentSearchHistoryDto.setId(updatedEntity.getId());

            return ResponseMapper.toDataResponseSuccess(recentSearchHistoryDto);
        }
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
