package com.edu.schema;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "username is mandatory")
    @NotNull(message = "username must not be null")
    @Column(unique = true, nullable = false)
    private String username;

    @NotBlank(message = "firstName is mandatory")
    @NotNull(message = "firstName must not be null")
    @Pattern(regexp = "^[A-Za-z]+$", message = "First name must contain only letters")
    //@Column(nullable = false)
    private String firstName;

    @NotBlank(message = "Last Name is mandatory")
    @NotNull(message = "lastName must not be null")
    //@Column(nullable = false)
    @Pattern(regexp = "^[A-Za-z]+$", message = "Last name must contain only letters")
    private String lastName;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Invalid email format")
    @NotNull(message = "email must not be null")
    //@Column(nullable = false, unique = true)
    private String email;

    @NotBlank(message = "Age is mandatory")
    @NotNull(message = "age must not be null")
    @Min(value = 18, message = "Age should be at least 18")
    private String age;

    @NotBlank(message = "Address is mandatory")
    @NotNull(message = "address must not be null")
    private String address;

    private String password;

    public Employee() {
    }

    public Employee(String username, String firstName, String lastName, String email, String age, String address, String password) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.age = age;
        this.address = address;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
