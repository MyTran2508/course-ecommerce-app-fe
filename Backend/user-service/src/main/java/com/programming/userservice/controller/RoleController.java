package com.programming.userservice.controller;

import com.main.progamming.common.controller.BaseApiImpl;
import com.main.progamming.common.error.exception.ResourceNotFoundException;
import com.main.progamming.common.response.DataResponse;
import com.main.progamming.common.response.ListResponse;
import com.main.progamming.common.service.BaseService;
import com.main.progamming.common.util.SystemUtil;
import com.programming.userservice.domain.dto.RoleDto;
import com.programming.userservice.domain.dto.UserRolesDto;
import com.programming.userservice.domain.mapper.RoleMapper;
import com.programming.userservice.domain.persistent.entity.Role;
import com.programming.userservice.domain.persistent.entity.User;
import com.programming.userservice.domain.persistent.entity.UserLog;
import com.programming.userservice.domain.persistent.enumrate.ActionName;
import com.programming.userservice.domain.persistent.enumrate.ActionObject;
import com.programming.userservice.repository.RoleRepository;
import com.programming.userservice.service.RoleService;
import com.programming.userservice.service.UserLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users/role")
public class RoleController extends BaseApiImpl<Role, RoleDto> {

    private final RoleService roleService;

    private final RoleRepository roleRepository;

    private final UserLogService userLogService;

    private final RoleMapper roleMapper;

    @Override
    protected BaseService<Role, RoleDto> getBaseService() {
        return this.roleService;
    }

    @Override
    public DataResponse<String> add(RoleDto roleDto) {

        DataResponse<String> response = super.add(roleDto);

        String stResult = response.getData();

        String roleId = stResult.split(": ")[1].trim();

        Role role = roleRepository.findById(roleId).orElse(null);

        // Add log
        UserLog userLog = UserLog.builder()
                .userName(SystemUtil.getCurrentUsername())
                .ip(SystemUtil.getUserIP())
                .actionKey(roleId)
                .actionObject(ActionObject.ROLE)
                .actionName(ActionName.CREATE)
                .description(userLogService.writePersistLog(Role.class, role, true, 0))
                .build();
        userLogService.addLog(userLog);

        return response;
    }

    @Override
    public DataResponse<RoleDto> update(RoleDto roleDto, String id) {
        Optional<Role> optionalRole = roleRepository.findById(id);
        if (optionalRole.isEmpty()) {
            throw new ResourceNotFoundException(id + " does not exists in DB");
        }
        Role role = optionalRole.get();

        DataResponse<RoleDto> response = super.update(roleDto, id);

        // Add log
        UserLog userLog = UserLog.builder()
                .userName(SystemUtil.getCurrentUsername())
                .ip(SystemUtil.getUserIP())
                .actionKey(id)
                .actionObject(ActionObject.ROLE)
                .actionName(ActionName.UPDATE)
                .description(userLogService.writeUpdateLog(Role.class, role, roleMapper.dtoToEntity(response.getData()), true, 0))
                .build();

        userLogService.addLog(userLog);

        return response;
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
