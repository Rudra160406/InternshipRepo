package com.example.ems.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record HodDto(
        @NotBlank(message = "Name is required")
        String name,

        @NotBlank(message = "Email is required")
        @Email(message = "Invalid email format")
        String email,

        @NotNull(message = "Salary is required")
        @Positive(message = "Salary must be greater than 0")
        Double salary,

        @Valid
        @NotNull(message = "Address is required")
        AddressDto address,

        @NotNull(message = "Department ID is required")
        Long departmentId
) {
}
