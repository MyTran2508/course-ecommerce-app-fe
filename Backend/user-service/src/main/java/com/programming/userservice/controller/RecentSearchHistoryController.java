package com.programming.userservice.controller;

import com.main.progamming.common.response.DataResponse;
import com.main.progamming.common.response.ListResponse;
import com.programming.userservice.domain.dto.RecentSearchHistoryDto;
import com.programming.userservice.domain.persistent.enumrate.ModuleSearch;
import com.programming.userservice.service.RecentSearchHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users/recent-search-history")
public class RecentSearchHistoryController {

    private final RecentSearchHistoryService recentSearchHistoryService;

    @GetMapping("/get/{username}/{moduleSearch}")
    public ListResponse<RecentSearchHistoryDto> getByUserNameAndModuleName(
            @PathVariable("username") final String username,
            @PathVariable("moduleSearch") final ModuleSearch moduleSearch) {

        return recentSearchHistoryService.getByUserNameAndModuleName(username, moduleSearch);
    }

    @PostMapping("/add")
    public DataResponse<RecentSearchHistoryDto> create(
            @RequestBody RecentSearchHistoryDto recentSearchHistoryDto
    ) {

        return recentSearchHistoryService.create(recentSearchHistoryDto);
    }

    @DeleteMapping("/delete/{username}/{moduleSearch}")
    public DataResponse<String> delete(
            @PathVariable("username") final String username,
            @PathVariable("moduleSearch") final ModuleSearch moduleSearch
    ) {
        return recentSearchHistoryService.deleteByUsernameAndModuleSearch(username, moduleSearch);
    }
}
