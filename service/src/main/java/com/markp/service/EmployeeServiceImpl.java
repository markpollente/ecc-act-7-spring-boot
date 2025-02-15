package com.markp.service;

import com.markp.dto.EmployeeDto;
import com.markp.exception.ResourceNotFoundException;
import com.markp.mapper.EmployeeMapper;
import com.markp.mapper.RoleMapper;
import com.markp.model.Employee;
import com.markp.model.HelpdeskTicket;
import com.markp.model.Role;
import com.markp.repository.EmployeeRepository;
import com.markp.repository.HelpdeskTicketRepository;
import com.markp.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository employeeRepository;
    private RoleRepository roleRepository;
    private HelpdeskTicketRepository helpdeskTicketRepository;

    @Override
    public EmployeeDto createEmployee(EmployeeDto employeeDto) {

        Employee employee = EmployeeMapper.mapToEmployee(employeeDto);
        Employee savedEmployee = employeeRepository.save(employee);

        return EmployeeMapper.mapToEmployeeDto(savedEmployee);
    }

    @Override
    public EmployeeDto getEmployeeById(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Employee does not exist with given id: " + employeeId));

        return EmployeeMapper.mapToEmployeeDto(employee);
    }

    @Override
    public List<EmployeeDto> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        return employees.stream().map((employee) -> EmployeeMapper.mapToEmployeeDto(employee))
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeDto updateEmployee(Long employeeId, EmployeeDto updatedEmployee) {

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Employee does not exist with given id: " + employeeId));

        employee.setFirstName(updatedEmployee.getFirstName());
        employee.setLastName(updatedEmployee.getLastName());
        employee.setEmail(updatedEmployee.getEmail());
        employee.setAge(updatedEmployee.getAge());
        employee.setAddress(updatedEmployee.getAddress());
        employee.setContactNumber(updatedEmployee.getContactNumber());
        employee.setEmploymentStatus(updatedEmployee.getEmploymentStatus());
        if (updatedEmployee.getRoles() != null) {
            employee.setRoles(updatedEmployee.getRoles().stream()
                    .map(RoleMapper::mapToRole)
                    .collect(Collectors.toList()));
        }

        Employee updatedEmployeeObj = employeeRepository.save(employee);

        return EmployeeMapper.mapToEmployeeDto(updatedEmployeeObj);
    }

    @Override
    public void deleteEmployee(Long employeeId) {
        List<HelpdeskTicket> tickets = helpdeskTicketRepository.findByAssigneeId(employeeId);

        for (HelpdeskTicket ticket : tickets) {
            ticket.setAssignee(null);
            helpdeskTicketRepository.save(ticket);
        }

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Employee does not exist with given id: " + employeeId));

        employeeRepository.deleteById(employeeId);
    }

    @Override
    public EmployeeDto assignRoleToEmployee(Long employeeId, Long roleId) {

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Employee does not exist with given id: " + employeeId));

        Role role = roleRepository.findById(roleId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Role does not exist with given id: " + roleId));

        employee.getRoles().add(role);
        Employee updatedEmployee = employeeRepository.save(employee);
        return EmployeeMapper.mapToEmployeeDto(updatedEmployee);
    }
}
