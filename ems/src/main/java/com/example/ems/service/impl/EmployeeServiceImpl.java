package com.example.ems.service.impl;

import com.example.ems.dto.EmployeeDto;
import com.example.ems.dto.EmployeeResponseDto;
import com.example.ems.dto.SimpleDepartmentDto;
import com.example.ems.entity.Address;
import com.example.ems.entity.Department;
import com.example.ems.entity.NormalEmployee;
import com.example.ems.entity.Employee;
import com.example.ems.repository.DepartmentRepository;
import com.example.ems.repository.EmployeeRepository;
import com.example.ems.service.EmployeeService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;

    @Override
    public EmployeeResponseDto createEmployee(EmployeeDto dto) {

        log.info("Creating NORMAL employee with email: {}", dto.email());

        NormalEmployee employee = new NormalEmployee();
        employee.setName(dto.name());
        employee.setEmail(dto.email());
        employee.setSalary(dto.salary());

        log.debug("Fetching departments for IDs: {}", dto.departmentIds());
        Set<Department> departments =
                new HashSet<>(departmentRepository.findAllById(dto.departmentIds()));
        employee.setDepartments(departments);

        Address address = new Address();
        address.setCity(dto.address().city());
        address.setState(dto.address().state());
        address.setPincode(dto.address().pincode());
        employee.setAddress(address);

        NormalEmployee savedEmployee = (NormalEmployee) employeeRepository.save(employee);

        log.info("Employee created successfully with ID: {}", savedEmployee.getId());

        return toResponse(savedEmployee);
    }

    @Transactional(readOnly = true)
    @Override
    public List<EmployeeResponseDto> getAllEmployees() {

        log.info("Fetching all NORMAL employees");

        List<EmployeeResponseDto> employees = employeeRepository.findAllNormalEmployees()
                .stream()
                .map(this::toResponse)
                .toList();

        log.info("Total normal employees fetched: {}", employees.size());

        return employees;
    }

    private EmployeeResponseDto toResponse(Employee employee) {

        log.debug("Mapping employee entity to response DTO for ID: {}", employee.getId());

        List<SimpleDepartmentDto> deps = employee.getDepartments() == null
                ? List.of()
                : employee.getDepartments()
                        .stream()
                        .map(d -> new SimpleDepartmentDto(d.getId(), d.getDepartmentName()))
                        .collect(Collectors.toList());

        return new EmployeeResponseDto(
                employee.getId(),
                employee.getName(),
                employee.getEmail(),
                employee.getSalary(),
                employee.getAddress(),
                deps
        );
    }
}
