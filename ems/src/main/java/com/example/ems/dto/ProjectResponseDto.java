package com.example.ems.dto;

import lombok.*;
import com.example.ems.entity.Address;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectResponseDto {

    private Long id;
    private String projectName;
    private String status;

    private Long employeeId;
    private String employeeName;
    private String employeeEmail;
    private Address employeeAddress;
}
