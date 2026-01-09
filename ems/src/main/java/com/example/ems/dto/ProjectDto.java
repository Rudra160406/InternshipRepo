package com.example.ems.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDto {

    @NotBlank(message = "Project name is required")
    private String projectName;

    @NotBlank(message = "Status is required")
    private String status;

    @NotNull(message = "Employee ID is required")
    private Long employeeId;
}
