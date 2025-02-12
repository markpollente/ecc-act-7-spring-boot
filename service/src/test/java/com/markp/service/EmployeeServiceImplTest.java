package com.markp.service;

import com.markp.dto.EmployeeDto;
import com.markp.exception.ResourceNotFoundException;
import com.markp.model.Employee;
import com.markp.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private Employee employee;
    private EmployeeDto employeeDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        employee = new Employee(1L, "First", "Last", "first.last@gmail.com", 1L, "Blk 123 Rob Homes, Antipolo", "09123456780", "Active");
        employeeDto = new EmployeeDto(1L, "First", "Last", "first.last@gmail.com", 1L, "Blk 123 Rob Homes, Antipolo", "09123456780", "Active");
    }

    @Test
    void createEmployee() {
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

        EmployeeDto savedEmployee = employeeService.createEmployee(employeeDto);

        assertNotNull(savedEmployee);
        assertEquals(employeeDto.getFirstName(), savedEmployee.getFirstName());
        verify(employeeRepository, times(1)).save(any(Employee.class));
    }

    @Test
    void getEmployeeById() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));

        EmployeeDto foundEmployee = employeeService.getEmployeeById(1L);

        assertNotNull(foundEmployee);
        assertEquals(employeeDto.getFirstName(), foundEmployee.getFirstName());
        verify(employeeRepository, times(1)).findById(1L);
    }

    @Test
    void getAllEmployees() {
        when(employeeRepository.findAll()).thenReturn(Arrays.asList(employee));

        List<EmployeeDto> employees = employeeService.getAllEmployees();

        assertNotNull(employees);
        assertEquals(1, employees.size());
        verify(employeeRepository, times(1)).findAll();
    }

    @Test
    void updateEmployee() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

        EmployeeDto updatedEmployee = employeeService.updateEmployee(1L, employeeDto);

        assertNotNull(updatedEmployee);
        assertEquals(employeeDto.getFirstName(), updatedEmployee.getFirstName());
        verify(employeeRepository, times(1)).findById(1L);
        verify(employeeRepository, times(1)).save(any(Employee.class));
    }

    @Test
    void deleteEmployee() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        doNothing().when(employeeRepository).deleteById(1L);

        employeeService.deleteEmployee(1L);

        verify(employeeRepository, times(1)).findById(1L);
        verify(employeeRepository, times(1)).deleteById(1L);
    }

    @Test
    void getEmployeeById_NotFound() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> employeeService.getEmployeeById(1L));
        verify(employeeRepository, times(1)).findById(1L);
    }
}