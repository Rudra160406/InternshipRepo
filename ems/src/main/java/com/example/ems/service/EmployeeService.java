package com.example.ems.service;

import com.example.ems.entity.Employee;
import com.example.ems.dto.EmployeeDto;
import com.example.ems.dto.EmployeeResponseDto;

import java.util.List;

public interface EmployeeService {
    Employee createEmployee(EmployeeDto dto);

    List<EmployeeResponseDto> getAllEmployees();
}
