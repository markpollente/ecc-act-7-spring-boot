package com.markp.mapper;

import com.markp.dto.EmployeeDto;
import com.markp.model.Employee;

public class EmployeeMapper {

    public static EmployeeDto mapToEmployeeDto(Employee employee) {
        if (employee == null) {
            return null;
        }
        return new EmployeeDto(
                employee.getId(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getEmail(),
                employee.getAge(),
                employee.getAddress(),
                employee.getContactNumber(),
                employee.getEmploymentStatus(),
                employee.getRole() != null ? RoleMapper.mapToRoleDto(employee.getRole()) : null
        );
    }

    public static Employee mapToEmployee(EmployeeDto employeeDto) {
        if (employeeDto == null) {
            return null;
        }
        return new Employee(
                employeeDto.getId(),
                employeeDto.getFirstName(),
                employeeDto.getLastName(),
                employeeDto.getEmail(),
                employeeDto.getAge(),
                employeeDto.getAddress(),
                employeeDto.getContactNumber(),
                employeeDto.getEmploymentStatus(),
                employeeDto.getRole() != null ? RoleMapper.mapToRole(employeeDto.getRole()) : null
        );
    }
}
