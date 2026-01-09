package com.example.ems.service;

import com.example.ems.dto.EmployeeDto;
import com.example.ems.dto.EmployeeResponseDto;

import java.util.List;

public interface EmployeeService {

    EmployeeResponseDto createEmployee(EmployeeDto dto);

    List<EmployeeResponseDto> getAllEmployees();
}
