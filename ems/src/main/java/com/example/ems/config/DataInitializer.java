package com.example.ems.config;

import com.example.ems.entity.Department;
import com.example.ems.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final DepartmentRepository departmentRepository;

    @Override
    public void run(String... args) {
        if (departmentRepository.count() > 0) {
            return;
        }

        departmentRepository.save(createDepartment("Engineering", "Software and platform engineering"));
        departmentRepository.save(createDepartment("Human Resources", "People operations and hiring"));
        departmentRepository.save(createDepartment("Finance", "Budgeting and financial operations"));
        departmentRepository.save(createDepartment("Operations", "Business and delivery operations"));
    }

    private Department createDepartment(String name, String description) {
        Department department = new Department();
        department.setDepartmentName(name);
        department.setDescription(description);
        return department;
    }
}
