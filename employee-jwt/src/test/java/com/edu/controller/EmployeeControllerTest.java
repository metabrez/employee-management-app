package com.edu.controller;

import com.edu.model.Employee;
import com.edu.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.*;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class EmployeeControllerTest {

    private MockMvc mockMvc;

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private EmployeeController employeeController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(employeeController).build();
    }

    @Test
    void createEmployee() throws Exception {
        Employee employee = new Employee();
        employee.setUsername("testUser");
        employee.setFirstName("Test");
        employee.setLastName("User");
        employee.setEmail("test@email.com");
        //employee.setPassword("password");
        employee.setAge(30);
        employee.setAddress("Test Address");

        when(employeeService.saveEmployee(any(Employee.class))).thenReturn(employee);

        mockMvc.perform(post("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"testUser\", \"firstName\": \"Test\",\"lastName\": \"User\", \"email\": \"test@email.com\", \"age\": 30, \"address\": \"Test Address\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("testUser"))
                .andExpect(jsonPath("$.firstName").value("Test"));

        verify(employeeService, times(1)).saveEmployee(any(Employee.class));

    }

    @Test
    void getEmployeeById() throws Exception {
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setUsername("testUser");

        when(employeeService.getEmployeeById(1L)).thenReturn(Optional.of(employee));
        mockMvc.perform(get("/api/employees/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("testUser"));

    }

    @Test
    void getAllEmployees() throws Exception {
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setUsername("testUser");
        employee.setFirstName("Test");
        employee.setLastName("User");
        employee.setEmail("test@email.com");
        employee.setAge(30);
        employee.setAddress("Test Address");

        Employee employee2 = new Employee();
        employee2.setId(2L);
        employee2.setUsername("testUser2");
        employee2.setFirstName("Test2");
        employee2.setLastName("User2");
        employee2.setEmail("test2@email.com");
        employee2.setAge(30);
        employee2.setAddress("Test Address2");

        List<Employee> employees = new ArrayList<>();
        employees.add(employee);
        employees.add(employee2);

        when(employeeService.getAllEmployees()).thenReturn(employees);
        mockMvc.perform(get("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].username").value("testUser"))
                .andExpect(jsonPath("$[1].username").value("testUser2"))
                .andExpect(jsonPath("$[0].firstName").value("Test"))
                .andExpect(jsonPath("$[1].firstName").value("Test2"));

        verify(employeeService, times(1)).getAllEmployees();


    }

    @Test
    void updateEmployee() throws Exception {
        Long employeeId = 1L;
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setUsername("testUser");
        employee.setFirstName("Test");
        employee.setLastName("User");
        employee.setEmail("test@email.com");
        employee.setAge(30);
        employee.setAddress("Test Address");
        when(employeeService.updateEmployee(any(Long.class),any(Employee.class))).thenReturn(employee);

        mockMvc.perform(put("/api/employees/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"testUser\", \"firstName\": \"Test\",\"lastName\": \"User\", \"email\": \"test@email.com\", \"age\": 30, \"address\": \"Test Address\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("testUser"))
                .andExpect(jsonPath("$.firstName").value("Test"));


    }

    @Test
    void deleteEmployee() throws Exception {
        Long employeeId = 1L;
        doNothing().when(employeeService).deleteEmployee(employeeId);
        mockMvc.perform(delete("/api/employees/{id}", employeeId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(employeeService, times(1)).deleteEmployee(employeeId);
    }
}