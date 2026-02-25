package com.example.ems.service;

import com.example.ems.dto.EmployeeResponseDto;
import java.util.List;

public interface EmployeeSearchService {

    List<EmployeeResponseDto> searchEmployees(
            String name,
            String email,
            String city,
            String department,
            Double minSalary,
            Double maxSalary
    );
}
