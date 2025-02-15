package com.markp.dto;

import com.markp.model.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Long age;
    private String address;
    private String contactNumber;
    private String employmentStatus;
    private List<RoleDto> roles;
}
