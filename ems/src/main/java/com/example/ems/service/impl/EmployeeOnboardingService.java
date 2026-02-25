package com.example.ems.service.impl;

import com.example.ems.dto.EmployeeDto;
import com.example.ems.entity.Address;
import com.example.ems.entity.Department;
import com.example.ems.entity.Employee;
import com.example.ems.entity.NormalEmployee;
import com.example.ems.entity.Project;
import com.example.ems.exception.InvalidDepartmentException;
import com.example.ems.exception.InvalidProjectException;
import com.example.ems.exception.OptionalDataException;
import com.example.ems.repository.DepartmentRepository;
import com.example.ems.repository.EmployeeRepository;
import com.example.ems.repository.ProjectRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeOnboardingService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final ProjectRepository projectRepository;

    
    @Transactional(
            rollbackFor = InvalidDepartmentException.class,
            noRollbackFor = OptionalDataException.class
    )
    public Employee onboardEmployee(
            EmployeeDto dto,
            List<Project> projects
    ) {

        log.info("Starting employee onboarding transaction");

        
        Set<Department> departments =
                new HashSet<>(departmentRepository.findAllById(dto.departmentIds()));

        if (departments.isEmpty()) {
            log.error("No valid departments found");
            throw new InvalidDepartmentException("Department is mandatory");
        }

        
        NormalEmployee employee = new NormalEmployee();
        employee.setName(dto.name());
        employee.setEmail(dto.email());
        employee.setSalary(dto.salary());

        Address address = new Address();
        address.setCity(dto.address().city());
        address.setState(dto.address().state());
        address.setPincode(dto.address().pincode());
        employee.setAddress(address);

        employee.setDepartments(departments);

        employeeRepository.save(employee);
        log.info("Employee saved with ID {}", employee.getId());

        
        for (Project project : projects) {
            try {
                if (project.getProjectName() == null || project.getProjectName().isBlank()) {
                    throw new InvalidProjectException("Project name missing");
                }

                project.setEmployee(employee);
                projectRepository.save(project);

                log.info("Project saved: {}", project.getProjectName());

            } catch (InvalidProjectException ex) {
                log.warn("Skipping invalid project, continuing transaction");
            }
        }

        
        if (employee.getSalary() != null && employee.getSalary() < 0) {
            log.warn("Salary is negative, but transaction will not rollback");
            throw new OptionalDataException("Salary warning");
        }

        log.info("Employee onboarding completed successfully");
        return employee;
    }
}
