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
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository employeeRepository;
    private RoleRepository roleRepository;
    private HelpdeskTicketRepository helpdeskTicketRepository;

    @Override
    @Transactional
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
    public EmployeeDto getEmployeeById(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Employee does not exist with given id: " + employeeId));
        return EmployeeMapper.mapToEmployeeDto(employee);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeeDto> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        return employees.stream().map(EmployeeMapper::mapToEmployeeDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public EmployeeDto updateEmployee(Long employeeId, EmployeeDto updatedEmployee) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Employee does not exist with given id: " + employeeId));
        if (updatedEmployee.getEmail() != null && !updatedEmployee.getEmail().equals(employee.getEmail()) &&
                employeeRepository.existsByEmail(updatedEmployee.getEmail())) {
            throw new ResourceNotFoundException("Email already exists: " + updatedEmployee.getEmail());
        }
        if (updatedEmployee.getLastName() != null) {
            employee.setLastName(updatedEmployee.getLastName());
        }
        if (updatedEmployee.getEmail() != null) {
            employee.setEmail(updatedEmployee.getEmail());
        }
        if (updatedEmployee.getAge() != null) {
            employee.setAge(updatedEmployee.getAge());
        }
        if (updatedEmployee.getAddress() != null) {
            employee.setAddress(updatedEmployee.getAddress());
        }
        if (updatedEmployee.getContactNumber() != null) {
            employee.setContactNumber(updatedEmployee.getContactNumber());
        }
        if (updatedEmployee.getEmploymentStatus() != null) {
            employee.setEmploymentStatus(updatedEmployee.getEmploymentStatus());
        }
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