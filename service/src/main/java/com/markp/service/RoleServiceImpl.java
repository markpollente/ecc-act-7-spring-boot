package com.markp.service;

import com.markp.dto.RoleDto;
import com.markp.exception.ResourceNotFoundException;
import com.markp.mapper.RoleMapper;
import com.markp.model.Role;
import com.markp.repository.RoleRepository;

import java.util.List;
import java.util.stream.Collectors;

public class RoleServiceImpl implements RoleService {

    private RoleRepository roleRepository;

    @Override
    public RoleDto createRole(RoleDto roleDto) {

        Role role = RoleMapper.mapToRole(roleDto);
        Role savedRole = roleRepository.save(role);
        return RoleMapper.mapToRoleDto(savedRole);
    }

    @Override
    public RoleDto getRoleByID(Long roleId) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Role does not exist with given id: " + roleId));
        return RoleMapper.mapToRoleDto(role);
    }

    @Override
    public List<RoleDto> getAllByRole() {
        List<Role> roles = roleRepository.findAll();
        return roles.stream().map(RoleMapper::mapToRoleDto)
                .collect(Collectors.toList());
    }

    @Override
    public RoleDto updateRole(Long roleId, RoleDto updatedRole) {

        Role role = roleRepository.findById(roleId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Role does not exist with given id: " + roleId));

        role.setName(updatedRole.getName());
        role.setDescription(updatedRole.getDescription());

        Role updatedRoleObj = roleRepository.save(role);

        return RoleMapper.mapToRoleDto(updatedRoleObj);
    }

    @Override
    public void deleteRole(Long roleId) {

        Role role = roleRepository.findById(roleId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Role does not exist with given id: " + roleId));

        roleRepository.deleteById(roleId);
    }
}
