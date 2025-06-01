package com.edu.service;

import com.edu.exception.UserAlreadyExistsException;
import com.edu.schema.Employee;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    private static final Logger log = LoggerFactory.getLogger(EmployeeService.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    public Employee saveEmployee(Employee employee) {
        employeeRepository.findByUsername(employee.getUsername()).ifPresent(e -> {
            throw new UserAlreadyExistsException("Employee already exists");
        });

        Employee savedEmployee = employeeRepository.save(employee);
        log.info("Employee saved : " + savedEmployee);
        return savedEmployee;
    }

    public Optional<Employee> getEmployeeById(Long id) {

        return Optional.ofNullable(employeeRepository.findById(id).orElseThrow(() -> new RuntimeException("Employee not found with id: " + id)));
    }

    public List<Employee> getAllEmployees() {
        log.info("Get all employees");
        return employeeRepository.findAll();
    }

    public Employee updateEmployee(Long id, @Valid Employee employee) {

        employeeRepository.findById(id).orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));

        return employeeRepository.save(employee);
    }

    public void deleteEmployee(Long id) {
        log.info("Delete an employee with id: " + id);
        getEmployeeById(id).ifPresent(employee -> employeeRepository.delete(employee));
        log.info("Delete an employee with id: " + id);
    }
}
