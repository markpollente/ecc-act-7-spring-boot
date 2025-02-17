package com.markp.service;

import com.markp.dto.RoleDto;
import com.markp.exception.ResourceNotFoundException;
import com.markp.mapper.RoleMapper;
import com.markp.model.Role;
import com.markp.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {

    private RoleRepository roleRepository;

    @Override
    @Transactional
    public RoleDto createRole(RoleDto roleDto) {
        if (roleDto.getName() == null || roleDto.getName().isEmpty()) {
            throw new ResourceNotFoundException("Role name is required");
        }
        Role role = RoleMapper.mapToRole(roleDto);
        Role savedRole = roleRepository.save(role);
        return RoleMapper.mapToRoleDto(savedRole);
    }

    @Override
    @Transactional(readOnly = true)
    public RoleDto getRoleByID(Long roleId) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Role does not exist with given id: " + roleId));
        return RoleMapper.mapToRoleDto(role);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoleDto> getAllRoles() {
        List<Role> roles = roleRepository.findAll();
        return roles.stream().map(RoleMapper::mapToRoleDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public RoleDto updateRole(Long roleId, RoleDto updatedRole) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Role does not exist with given id: " + roleId));
        if (updatedRole.getName() != null) {
            role.setName(updatedRole.getName());
        }
        if (updatedRole.getDescription() != null) {
            role.setDescription(updatedRole.getDescription());
        }
        Role updatedRoleObj = roleRepository.save(role);
        return RoleMapper.mapToRoleDto(updatedRoleObj);
    }

    @Override
    @Transactional
    public void deleteRole(Long roleId) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Role does not exist with given id: " + roleId));
        roleRepository.deleteById(roleId);
    }
}