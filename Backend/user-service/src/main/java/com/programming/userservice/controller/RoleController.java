package com.programming.userservice.controller;

import com.main.progamming.common.controller.BaseApiImpl;
import com.main.progamming.common.response.DataResponse;
import com.main.progamming.common.response.ListResponse;
import com.main.progamming.common.service.BaseService;
import com.programming.userservice.domain.dto.RoleDto;
import com.programming.userservice.domain.dto.UserRolesDto;
import com.programming.userservice.domain.persistent.entity.Role;
import com.programming.userservice.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users/role")
public class RoleController extends BaseApiImpl<Role, RoleDto> {

    private final RoleService roleService;

    @Override
    protected BaseService<Role, RoleDto> getBaseService() {
        return this.roleService;
    }

    @Override
    public DataResponse<String> add(RoleDto roleDto) {
        return super.add(roleDto);
    }

    @Override
    public DataResponse<RoleDto> update(RoleDto roleDto, String id) {
        return super.update(roleDto, id);
    }

    @Override
    public DataResponse<RoleDto> getById(String id) {
        return super.getById(id);
    }

    @Override
    public ListResponse<RoleDto> getAll() {
        return super.getAll();
    }

    @GetMapping("/get-user-roles/{username}")
    public DataResponse<UserRolesDto> getUserRoles(@PathVariable String username) {
        return roleService.getUserRoles(username);
    }
}
