package com.markp.impl;

import com.markp.dto.EmployeeDto;
import com.markp.exception.ResourceNotFoundException;
import com.markp.logging.LogExecutionTime;
import com.markp.mapper.EmployeeMapper;
import com.markp.mapper.RoleMapper;
import com.markp.model.Employee;
import com.markp.model.HelpdeskTicket;
import com.markp.model.Role;
import com.markp.repository.EmployeeRepository;
import com.markp.repository.HelpdeskTicketRepository;
import com.markp.repository.RoleRepository;
import com.markp.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private HelpdeskTicketRepository helpdeskTicketRepository;

    @Override
    @Transactional
    @LogExecutionTime
    public EmployeeDto createEmployee(EmployeeDto employeeDto) {
        if (employeeDto.getFirstName() == null || employeeDto.getFirstName().isEmpty()) {
            throw new ResourceNotFoundException("First name is required.");
        }
        if (employeeDto.getLastName() == null || employeeDto.getLastName().isEmpty()) {
            throw new ResourceNotFoundException("Last name is required.");
        }
        if (employeeDto.getEmail() == null || employeeDto.getEmail().isEmpty()) {
            throw new ResourceNotFoundException("Email is required.");
        } else if (employeeRepository.existsByEmail(employeeDto.getEmail())) {
            throw new ResourceNotFoundException("Email already exists: " + employeeDto.getEmail());
        }
        Employee employee = EmployeeMapper.mapToEmployee(employeeDto);
        Employee savedEmployee = employeeRepository.save(employee);
        return EmployeeMapper.mapToEmployeeDto(savedEmployee);
    }

    @Override
    @Transactional(readOnly = true)
    @LogExecutionTime
    public EmployeeDto getEmployeeById(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Employee does not exist with given id: " + employeeId));
        return EmployeeMapper.mapToEmployeeDto(employee);
    }

    @Override
    @Transactional(readOnly = true)
    @LogExecutionTime
    public List<EmployeeDto> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        return employees.stream().map(EmployeeMapper::mapToEmployeeDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    @LogExecutionTime
    public EmployeeDto updateEmployee(Long employeeId, EmployeeDto updatedEmployee) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Employee does not exist with given id: " + employeeId));
        if (!updatedEmployee.getEmail().equals(employee.getEmail()) &&
                employeeRepository.existsByEmail(updatedEmployee.getEmail())) {
            throw new ResourceNotFoundException("Email already exists: " + updatedEmployee.getEmail());
        }
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
    @Transactional
    @LogExecutionTime
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
    @Transactional
    @LogExecutionTime
    public EmployeeDto assignRoleToEmployee(Long employeeId, Long roleId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Employee does not exist with given id: " + employeeId));
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Role does not exist with given id: " + roleId));
        if (employee.getRoles().stream().noneMatch(r -> r.getId().equals(roleId))) {
            employee.getRoles().add(role);
        }
        Employee updatedEmployee = employeeRepository.save(employee);
        return EmployeeMapper.mapToEmployeeDto(updatedEmployee);
    }
}