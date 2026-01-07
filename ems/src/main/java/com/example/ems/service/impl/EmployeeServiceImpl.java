package com.example.ems.service.impl;

import com.example.ems.dto.EmployeeDto;
import com.example.ems.dto.EmployeeResponseDto;
import com.example.ems.dto.SimpleDepartmentDto;
import com.example.ems.entity.Address;
import com.example.ems.entity.Department;
import com.example.ems.entity.Employee;
import com.example.ems.repository.DepartmentRepository;
import com.example.ems.repository.EmployeeRepository;
import com.example.ems.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;

    @Override
    public Employee createEmployee(EmployeeDto dto) {
        Employee employee = new Employee();
        employee.setName(dto.name());
        employee.setEmail(dto.email());
        employee.setSalary(dto.salary());

        Set<Department> department = new HashSet<>(departmentRepository.findAllById(dto.departmentIds()));
        employee.setDepartments(department);

        Address address = new Address();
        address.setCity(dto.address().city());
        address.setState(dto.address().state());
        address.setPincode(dto.address().pincode());

        employee.setAddress(address);

        return employeeRepository.save(employee);
    }

    @Transactional(readOnly = true)
    @Override
    public List<EmployeeResponseDto> getAllEmployees() {
        return employeeRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private EmployeeResponseDto toResponse(Employee employee) {
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
                deps);
    }
}