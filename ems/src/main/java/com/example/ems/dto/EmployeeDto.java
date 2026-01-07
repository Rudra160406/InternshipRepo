package com.example.ems.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.util.Set;

public record EmployeeDto(

        @NotBlank(message = "Name is required")
        String name,

        @NotBlank(message = "Email is required")
        @Email(message = "Invalid email format")
        String email,

        @Positive(message = "Salary must be greater than 0")
        double salary,

        @Valid
        @NotNull(message = "Address is required")
        AddressDto address,

        @NotEmpty(message = "At least one department is required")
        Set<Long> departmentIds
) {}
