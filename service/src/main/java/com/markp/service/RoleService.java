package com.markp.service;

import com.markp.dto.RoleDto;

import java.util.List;

public interface RoleService {

    RoleDto createRole(RoleDto roleDto);

    RoleDto getRoleByID(Long roleId);

    List<RoleDto> getAllByRole();

    RoleDto updateRole(Long roleId, RoleDto updatedRole);

    void deleteRole(Long roleId);

}
