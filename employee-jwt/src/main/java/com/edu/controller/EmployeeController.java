package com.edu.controller;

import com.edu.exception.ErrorDetails;
import com.edu.exception.ResourceNotFoundException;
import com.edu.exception.UserNotFoundException;
import com.edu.schema.ApiResponseIs;
import com.edu.schema.Employee;
import com.edu.service.EmployeeRepository;
import com.edu.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

import io.swagger.v3.oas.annotations.responses.ApiResponse; // ALIAS for OpenAPI Annotation
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/employees")
//@Api(value = "Employee Management System", description = "Operations pertaining to employee in Employee Management System")
@Tag(name = "Employee Management System", description = "Operations pertaining to employee in Employee Management System")
public class EmployeeController {

    private static final Logger log = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployeeRepository employeeRepository;


    @PostMapping("/create")
    //@ApiOperation(value = "Create a new employee", notes = "Provide employee details to create a new employee", response = Employee.class)
    @Operation(summary = "Create a new employee record", description = "Adds a new employee record to the system with unique username.") // Corrected summary/description
    @ApiResponse(responseCode = "201", description = "Employee created successfully",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Employee.class))) // Using APIResponseIs DTO
    @ApiResponse(responseCode = "400", description = "Invalid input or missing required fields",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorDetails.class)))
    @ApiResponse(responseCode = "409", description = "Employee with the given username already exists",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Employee.class))) // Using APIResponseIs DTO
    @RequestBody(description = "Employee object to be created", required = true,
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Employee.class)))
    @GetMapping("/{id}")
    public ResponseEntity<Employee> createEmployee(@Valid @RequestBody Employee employee) {

       /* employeeRepository.findByUsername(employee.getUsername()).ifPresent(e -> {
            throw new UserAlreadyExistsException("Employee already exists");
        });*/

        log.info("Creating employee : " + employee.getUsername());
        Employee savedEmployee = employeeService.saveEmployee(employee);
        return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    //@ApiOperation(value = "Get an employee by ID", notes = "Provide an ID to look up specific employee", response = Employee.class)
    @Operation(summary = "Get an employee by ID", description = "Provide an ID to look up specific employee")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable("id") Long id) {
        log.info("Get an employee by ID : " + id);
        Employee employee = (employeeRepository.findById(id).orElseThrow(() -> new UserNotFoundException("user not found")));
       /* if (employee.isPresent()) {
            return new ResponseEntity<>(employee.get(), HttpStatus.OK);

        }*/
        return ResponseEntity.ok(employee);
    }

    @GetMapping
    //@ApiOperation(value = "Get all employees", notes = "Retrieve a list of all employees", response = Employee.class)
    @Operation(summary = "Get all employees", description = "Retrieve a list of all employees")
    public List<Employee> getAllEmployees() {
        log.info("Get all employees");
        return employeeService.getAllEmployees();
    }

    @PutMapping("/{id}")
    //@ApiOperation(value = "Update an existing employee", notes = "Provide employee details to update an existing employee", response = Employee.class )
    @Operation(summary = "Update an existing employee", description = "Provide employee details to update an existing employee")
    public ResponseEntity<Employee> updateEmployee(@PathVariable("id") Long id, @Valid @RequestBody Employee employee) {
        Employee updateEmployee = employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee", "id", id));
        updateEmployee.setUsername(employee.getUsername());
        updateEmployee.setFirstName(employee.getFirstName());
        updateEmployee.setLastName(employee.getLastName());
        updateEmployee.setEmail(employee.getEmail());
        updateEmployee.setAge(employee.getAge());
        updateEmployee.setAddress(employee.getAddress());
        updateEmployee.setPassword(employee.getPassword());

        Employee savedEmployee = employeeRepository.save(updateEmployee);


        log.info("Update an existing employee : " + employee.getUsername());

        return new ResponseEntity<>(savedEmployee, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    //@ApiOperation(value = "Delete an employee by ID", notes = "Provide an ID to delete a specific employee")
    @Operation(summary = "Delete an employee by ID", description = "Provide an ID to delete a specific employee")

    public ResponseEntity<ApiResponseIs> deleteEmployee(@PathVariable Long id) {
        Employee employee = (employeeRepository.findById(id).orElseThrow(() -> new UserNotFoundException("Employee", "id", id)));
        log.info("Delete an employee by ID : " + id);
        employeeService.deleteEmployee(employee.getId());
        //return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        //return ResponseEntity.noContent().build();
        return ResponseEntity.ok(new ApiResponseIs("Deleted an employee successfully"));
    }
}
