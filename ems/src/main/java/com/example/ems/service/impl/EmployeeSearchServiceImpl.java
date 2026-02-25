package com.example.ems.service.impl;

import com.example.ems.dto.EmployeeResponseDto;
import com.example.ems.dto.SimpleDepartmentDto;
import com.example.ems.entity.Employee;
import com.example.ems.repository.EmployeeRepository;
import com.example.ems.service.EmployeeSearchService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EmployeeSearchServiceImpl implements EmployeeSearchService {

    private final EmployeeRepository employeeRepository;

    @Override
    public List<EmployeeResponseDto> searchEmployees(
            String name,
            String email,
            String city,
            String department,
            Double minSalary,
            Double maxSalary
    ) {
        return employeeRepository
                .searchEmployees(name, email, city, department, minSalary, maxSalary)
                .stream()
                .map(this::toDto)
                .toList();
    }

    private EmployeeResponseDto toDto(Employee e) {
        return new EmployeeResponseDto(
                e.getId(),
                e.getName(),
                e.getEmail(),
                e.getSalary(),
                e.getAddress(),
                e.getDepartments().stream()
                        .map(d -> new SimpleDepartmentDto(d.getId(), d.getDepartmentName()))
                        .toList()
        );
    }
}
