package com.programming.userservice.service;

import com.main.progamming.common.dto.SearchKeywordDto;
import com.main.progamming.common.error.exception.DataNotFoundException;
import com.main.progamming.common.model.BaseMapper;
import com.main.progamming.common.repository.BaseRepository;
import com.main.progamming.common.response.DataResponse;
import com.main.progamming.common.response.ResponseMapper;
import com.main.progamming.common.service.BaseServiceImpl;
import com.programming.userservice.domain.dto.ModuleDto;
import com.programming.userservice.domain.dto.RoleDetailDto;
import com.programming.userservice.domain.dto.RoleDto;
import com.programming.userservice.domain.dto.UserRolesDto;
import com.programming.userservice.domain.mapper.RoleMapper;
import com.programming.userservice.domain.persistent.entity.Role;
import com.programming.userservice.domain.persistent.entity.RoleDetail;
import com.programming.userservice.domain.persistent.entity.User;
import com.programming.userservice.domain.persistent.enumrate.ModuleName;
import com.programming.userservice.repository.RoleRepository;
import com.programming.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class RoleService extends BaseServiceImpl<Role, RoleDto> {

    private final RoleRepository roleRepository;

    private final UserRepository userRepository;

    private final RoleMapper roleMapper;

    @Override
    protected BaseRepository<Role> getBaseRepository() {
        return this.roleRepository;
    }

    @Override
    protected BaseMapper<Role, RoleDto> getBaseMapper() {
        return this.roleMapper;
    }

    @Override
    protected Page<RoleDto> getPageResults(SearchKeywordDto searchKeywordDto, Pageable pageable) {
        return null;
    }

    @Override
    protected List<RoleDto> getListSearchResults(String keyword) {
        return null;
    }

    public DataResponse<UserRolesDto> getUserRoles(String username) {

         User user = userRepository.findByUserName(username);
         if (user == null) {
             throw new DataNotFoundException("User not found");
         }

         UserRolesDto userRolesDto = new UserRolesDto();
         userRolesDto.setId(user.getId());
         userRolesDto.setUsername(user.getUsername());
         userRolesDto.setEmail(user.getEmail());

         Map<ModuleName, Set<String>> roleDetailDtoMap = new HashMap<>();
         List<RoleDto> roles = new ArrayList<>();
         for (Role role: user.getRoles()) {
             roles.add(new RoleDto(role.getName()));

             for (RoleDetail roleDetail: role.getRoleDetails()) {
                 if (roleDetail.getModule().getIsDeleted()) {
                     continue;
                 }

                 Set<String> listUserRoleString = roleDetailDtoMap.get(roleDetail.getModule().getModuleName());
                 if (listUserRoleString == null) {
                     listUserRoleString = new HashSet<>();
                 }
                 if (roleDetail.getCanCreate()) {
                     listUserRoleString.add("canCreate");
                 }
                 if (roleDetail.getCanView()) {
                     listUserRoleString.add("canView");
                 }
                 if (roleDetail.getCanUpdate()) {
                     listUserRoleString.add("canUpdate");
                 }
                 if (roleDetail.getCanRemove()) {
                     listUserRoleString.add("canRemove");
                 }
                 if (roleDetail.getCanStatistics()) {
                     listUserRoleString.add("canStatistics");
                 }
                 roleDetailDtoMap.put(roleDetail.getModule().getModuleName(), listUserRoleString);
             }
         }

         List<RoleDetailDto> roleDetailDtos = new ArrayList<>();
         for (ModuleName key: roleDetailDtoMap.keySet()) {
            RoleDetailDto roleDetailDto = new RoleDetailDto();
            Set<String> listUserRoleString = roleDetailDtoMap.get(key);
            roleDetailDto.setModule(new ModuleDto(key));

            for (String roleString: listUserRoleString) {
                if (roleString.equals("canView")) {
                    roleDetailDto.setCanView(true);
                }
                if (roleString.equals("canCreate")) {
                    roleDetailDto.setCanCreate(true);
                }
                if (roleString.equals("canUpdate")) {
                    roleDetailDto.setCanUpdate(true);
                }
                if (roleString.equals("canRemove")) {
                    roleDetailDto.setCanRemove(true);
                }
                if (roleString.equals("canStatistics")) {
                    roleDetailDto.setCanStatistics(true);
                }
            }
             roleDetailDtos.add(roleDetailDto);
         }
         userRolesDto.setRoles(roles);
         userRolesDto.setRoleDetailDtos(roleDetailDtos);
         return ResponseMapper.toDataResponseSuccess(userRolesDto);
    }
}
