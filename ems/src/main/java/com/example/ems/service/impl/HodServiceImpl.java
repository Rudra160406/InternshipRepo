package com.example.ems.service.impl;

import com.example.ems.dto.EmployeeResponseDto;
import com.example.ems.dto.SimpleDepartmentDto;
import com.example.ems.entity.Department;
import com.example.ems.entity.Hod;
import com.example.ems.repository.DepartmentRepository;
import com.example.ems.repository.EmployeeRepository;
import com.example.ems.service.HodService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class HodServiceImpl implements HodService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;

    @Override
    public EmployeeResponseDto createHod(Hod hod) {

        log.info("Creating HOD with email: {}", hod.getEmail());

        if (hod.getDepartment() != null && hod.getDepartment().getId() != null) {
            Department dept = departmentRepository.findById(hod.getDepartment().getId())
                    .orElseThrow(() -> new RuntimeException("Department not found"));
            hod.setDepartment(dept);
        }

        Hod savedHod = (Hod) employeeRepository.save(hod);
        log.info("HOD created successfully with ID: {}", savedHod.getId());

        return toResponse(savedHod);
    }

    @Override
    public List<EmployeeResponseDto> getAllHods() {

        log.info("Fetching all HODs");

        List<EmployeeResponseDto> hods = employeeRepository.findAllHods()
                .stream()
                .map(e -> toResponse((Hod) e))
                .toList();

        log.info("Total HODs fetched: {}", hods.size());

        return hods;
    }

    private EmployeeResponseDto toResponse(Hod hod) {

        List<SimpleDepartmentDto> deps = hod.getDepartments() == null
                ? List.of()
                : hod.getDepartments()
                        .stream()
                        .map(d -> new SimpleDepartmentDto(d.getId(), d.getDepartmentName()))
                        .toList();

        return new EmployeeResponseDto(
                hod.getId(),
                hod.getName(),
                hod.getEmail(),
                hod.getSalary(),
                hod.getAddress(),
                deps
        );
    }
}
