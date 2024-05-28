package com.programming.userservice.domain.dto;

import com.programming.userservice.domain.persistent.entity.Module;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RoleDetailDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Boolean canView;

    private Boolean canCreate;

    private Boolean canUpdate;

    private Boolean canRemove;

    private Boolean canStatistics;

    private ModuleDto module;
}
