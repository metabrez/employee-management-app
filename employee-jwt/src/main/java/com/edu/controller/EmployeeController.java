package com.edu.controller;

import com.edu.model.Employee;
import com.edu.service.EmployeeService;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/employees")
//@Api(value = "Employee Management System", description = "Operations pertaining to employee in Employee Management System")
@Tag(name = "Employee Management System", description = "Operations pertaining to employee in Employee Management System")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;


    @PostMapping
    //@ApiOperation(value = "Create a new employee", notes = "Provide employee details to create a new employee", response = Employee.class)
    @Operation(summary = "Create a new employee", description = "Provide employee details to create a new employee")
    public Employee createEmployee(@RequestBody Employee employee) {
        return employeeService.saveEmployee(employee);
    }

    @GetMapping("/{id}")
   // @ApiOperation(value = "Get an employee by ID", notes = "Provide an ID to look up specific employee", response = Employee.class)
    @Operation(summary = "Get an employee by ID", description = "Provide an ID to look up specific employee")
    public Optional<Employee> getEmployeeById(@PathVariable Long id) {
        return employeeService.getEmployeeById(id);
    }

    @GetMapping
    //@ApiOperation(value = "Get all employees", notes = "Retrieve a list of all employees", response = Employee.class)
    @Operation(summary = "Get all employees", description = "Retrieve a list of all employees")
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @PutMapping("/{id}")
    //@ApiOperation(value = "Update an existing employee", notes = "Provide employee details to update an existing employee", response = Employee.class )
    @Operation(summary = "Update an existing employee", description = "Provide employee details to update an existing employee")
    public Employee updateEmployee(@PathVariable Long id, @RequestBody Employee employee) {

        employee.setId(id);
        //employee.setFirstName(employee.getFirstName());
        return employeeService.updateEmployee(id,employee);
    }

    @DeleteMapping("/{id}")
   // @ApiOperation(value = "Delete an employee by ID", notes = "Provide an ID to delete a specific employee")
    @Operation(summary = "Delete an employee by ID", description = "Provide an ID to delete a specific employee")
    public void deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
    }
}