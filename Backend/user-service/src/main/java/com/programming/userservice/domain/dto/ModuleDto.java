package com.programming.userservice.domain.dto;

import com.programming.userservice.domain.persistent.enumrate.ModuleName;
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
public class ModuleDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Integer id;

    private ModuleName moduleName;

    private Boolean isDeleted;

    public ModuleDto(ModuleName moduleName) {
        this.moduleName = moduleName;
    }
}
