package com.edu.service;

import com.edu.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public Employee saveEmployee(Employee employee) {

        return employeeRepository.save(employee);
    }

    public Optional<Employee> getEmployeeById(Long id) {

        return Optional.ofNullable(employeeRepository.findById(id).orElseThrow(() -> new RuntimeException("Employee not found with id: " + id)));
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee updateEmployee(Long id, Employee employee) {
        Optional<Employee> existingEmployee = getEmployeeById(id);
        if (existingEmployee.isPresent()) {
            // Update the existing employee with the new details
            Employee emp = existingEmployee.get();
            emp.setUsername(employee.getUsername());
            emp.setFirstName(employee.getFirstName());
            emp.setLastName(employee.getLastName());
            emp.setEmail(employee.getEmail());
            emp.setAge(employee.getAge());
            emp.setAddress(employee.getAddress());
            return employeeRepository.save(emp);
        } else {
            // Handle case where employee does not exist (could throw an exception or return null)
            return null;
        }
    }

    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }
}
