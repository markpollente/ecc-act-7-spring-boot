package com.markp.app.controller;

import com.markp.dto.RoleDto;
import com.markp.service.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/roles")
public class RoleController {

    private RoleService roleService;

    // create a role record
    @PostMapping
    public ResponseEntity<RoleDto> createRole(@RequestBody RoleDto roleDto) {
        RoleDto savedRole = roleService.createRole(roleDto);
        return new ResponseEntity<>(savedRole, HttpStatus.CREATED);
    }

    // view a role record
    @GetMapping("{id}")
    public ResponseEntity<RoleDto> getRoleById(@PathVariable("id") Long roleId) {
        RoleDto roleDto = roleService.getRoleByID(roleId);
        return ResponseEntity.ok(roleDto);
    }

    // list a role record
    @GetMapping
    public ResponseEntity<List<RoleDto>> getAllRoles() {
        List<RoleDto> roles = roleService.getAllRoles();
        return ResponseEntity.ok(roles);
    }

    // update a role record
    @PutMapping("{id}")
    public ResponseEntity<RoleDto> updateRole(@PathVariable("id") Long roleId,
                                              @RequestBody RoleDto updatedRole) {
        RoleDto roleDto = roleService.updateRole(roleId, updatedRole);
        return ResponseEntity.ok(roleDto);
    }

    // delete a role record
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteRole(@PathVariable("id") Long roleId) {
        roleService.deleteRole(roleId);
        return ResponseEntity.ok(("Role deleted successfully."));
    }
}
