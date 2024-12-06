package com.edu.service;

import com.edu.model.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;
    @InjectMocks
    private EmployeeService employeeService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveEmployee() {
        Employee employee = new Employee();
        employee.setUsername("testUser");
        employee.setFirstName("Test");
        employee.setLastName("User");
        employee.setEmail("test@test.com");
        employee.setPassword("password");
        employee.setAge(30);
        employee.setAddress("Test");

        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);
        Employee savedEmployee = employeeService.saveEmployee(employee);
        assertNotNull(savedEmployee);
        assertEquals(employee.getUsername(), savedEmployee.getUsername());
        assertEquals(employee.getFirstName(), savedEmployee.getFirstName());
        assertEquals(employee.getLastName(), savedEmployee.getLastName());
        assertEquals(employee.getEmail(), savedEmployee.getEmail());
        assertEquals(employee.getPassword(), savedEmployee.getPassword());
        assertEquals(employee.getAge(), savedEmployee.getAge());
        assertEquals(employee.getAddress(), savedEmployee.getAddress());
        verify(employeeRepository, times(1)).save(employee);
    }

    @Test
    void getEmployeeById() {
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setUsername("testUser");
        employee.setFirstName("Test");
        employee.setLastName("User");
        employee.setEmail("test@test.com");
        employee.setPassword("password");
        employee.setAge(30);
        employee.setAddress("Test");

        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        Optional<Employee> foundEmployee = employeeService.getEmployeeById(1L);
        assertNotNull(foundEmployee);
        assertEquals("testUser", foundEmployee.get().getUsername());

        verify(employeeRepository, times(1)).findById(1L);
    }

    @Test
    void getAllEmployees() {
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setUsername("testUser");
        employee.setFirstName("Test");
        employee.setLastName("User");
        employee.setEmail("test@test.com");
        employee.setPassword("password");
        employee.setAge(30);
        employee.setAddress("Test");

        Employee employee2 = new Employee();
        employee.setId(1L);
        employee.setUsername("testUser2");
        employee.setFirstName("Test2");
        employee.setLastName("User2");
        employee.setEmail("test2@test.com");
        employee.setPassword("password");
        employee.setAge(30);
        employee.setAddress("Test2");

        List<Employee> employees = new ArrayList<>();
        employees.add(employee);
        employees.add(employee2);
        when(employeeRepository.findAll()).thenReturn(employees);
        List<Employee> foundEmployees = employeeService.getAllEmployees();
        assertNotNull(foundEmployees);
        assertEquals(2, foundEmployees.size());
        verify(employeeRepository, times(1)).findAll();


    }

    @Test
    void updateEmployee() {
        Employee existingEmployee = new Employee();
        existingEmployee.setId(1L);
        existingEmployee.setUsername("olduser");
        existingEmployee.setFirstName("Old");
        existingEmployee.setLastName("User");
        existingEmployee.setEmail("olduser@example.com");
        existingEmployee.setAge(30);
        existingEmployee.setAddress("Old Address");

        Employee updatedEmployee = new Employee();
        updatedEmployee.setId(1L);
        updatedEmployee.setUsername("newuser");
        updatedEmployee.setFirstName("New");
        updatedEmployee.setLastName("User");
        updatedEmployee.setEmail("newuser@example.com");
        updatedEmployee.setAge(35);
        updatedEmployee.setAddress("New Address");

        when(employeeRepository.findById(1L)).thenReturn(Optional.of(existingEmployee));
        when(employeeRepository.save(any(Employee.class))).thenReturn(updatedEmployee);

        Employee result = employeeService.updateEmployee(1L,updatedEmployee);

        assertEquals("newuser", result.getUsername());
        assertEquals("New", result.getFirstName());
        assertEquals("User", result.getLastName());
        assertEquals("newuser@example.com", result.getEmail());
        assertEquals(35, result.getAge());
        assertEquals("New Address", result.getAddress());

        verify(employeeRepository, times(1)).findById(1L);
        verify(employeeRepository, times(1)).save(any(Employee.class));
    }


    @Test
    void deleteEmployee() {
        Long employeeId = 1L;
        // Call the deleteEmployee method
        employeeService.deleteEmployee(employeeId);
        // Verify that deleteById was called once with the correct ID
        verify(employeeRepository, times(1)).deleteById(employeeId);
    }


}