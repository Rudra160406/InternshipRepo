package com.example.ems.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record DepartmentDto(

        @NotBlank(message = "Department name is required")
        @Size(min = 2, max = 100, message = "Department name must be 2–100 characters")
        String departmentName,

        @Size(max = 255, message = "Description must be under 255 characters")
        String description
) {}
