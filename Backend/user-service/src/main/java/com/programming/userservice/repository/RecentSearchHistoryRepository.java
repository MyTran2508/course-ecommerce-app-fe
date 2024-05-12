package com.programming.userservice.repository;

import com.programming.userservice.domain.persistent.entity.RecentSearchHistory;
import com.programming.userservice.domain.persistent.enumrate.ModuleSearch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecentSearchHistoryRepository extends JpaRepository<RecentSearchHistory, String> {

    List<RecentSearchHistory> findByUsernameAndModuleSearch(String username, ModuleSearch moduleSearch);


    RecentSearchHistory findByUsernameAndModuleSearchAndKeywordAndKeywordType(
            String username, ModuleSearch moduleSearch, String keyword, Integer keywordType
    );
}
