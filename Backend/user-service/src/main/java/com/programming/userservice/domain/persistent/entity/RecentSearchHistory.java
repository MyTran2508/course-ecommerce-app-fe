package com.programming.userservice.domain.persistent.entity;

import com.programming.userservice.domain.persistent.enumrate.ModuleSearch;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(
        name = "recent_search_history"
)
public class RecentSearchHistory {

    @Id
    private String id;

    private String username;

    @Column(name ="search_choose")
    private String searchChoose;

    @Column(name = "search_keyword")
    private String searchKeyword;

    @Column(name = "module_search")
    private ModuleSearch moduleSearch;

    @Column(name = "count_history")
    private Integer countHistory;

    private Long created;

    @PrePersist
    protected void ensureId() {
        this.setId(UUID.randomUUID().toString());
        this.setCreated(System.currentTimeMillis());
    }

    @PreUpdate
    protected void setUpdated() {
        this.setCreated(System.currentTimeMillis());
    }
}
