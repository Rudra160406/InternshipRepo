package com.example.ems.controller;

import com.example.ems.dto.SimpleDepartmentDto;
import com.example.ems.entity.Department;
import com.example.ems.repository.DepartmentRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/departments")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
@Slf4j
public class DepartmentController {

    private final DepartmentRepository departmentRepository;

    @GetMapping
    public List<SimpleDepartmentDto> getAllDepartments() {
        ensureDefaultDepartments();
        List<Department> deps = departmentRepository.findAll();
        log.info("Departments fetched: {}", deps.size());
        return deps.stream()
                .map(d -> new SimpleDepartmentDto(d.getId(), d.getDepartmentName()))
                .toList();
    }

    private void ensureDefaultDepartments() {
        if (departmentRepository.count() > 0) {
            return;
        }

        List<Department> defaults = new ArrayList<>();
        defaults.add(buildDepartment("Engineering", "Software and platform engineering"));
        defaults.add(buildDepartment("Human Resources", "People operations and hiring"));
        defaults.add(buildDepartment("Finance", "Budgeting and financial operations"));
        defaults.add(buildDepartment("Operations", "Business and delivery operations"));

        departmentRepository.saveAll(defaults);
        log.info("Default departments created: {}", defaults.size());
    }

    private Department buildDepartment(String name, String description) {
        Department department = new Department();
        department.setDepartmentName(name);
        department.setDescription(description);
        return department;
    }
}
