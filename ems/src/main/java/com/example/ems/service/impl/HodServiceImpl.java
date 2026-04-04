package com.example.ems.service.impl;

import com.example.ems.dto.AddressDto;
import com.example.ems.dto.EmployeeResponseDto;
import com.example.ems.dto.HodDto;
import com.example.ems.dto.SimpleDepartmentDto;
import com.example.ems.entity.Address;
import com.example.ems.entity.Department;
import com.example.ems.entity.Hod;
import com.example.ems.exception.ResourceNotFoundException;
import com.example.ems.repository.DepartmentRepository;
import com.example.ems.repository.EmployeeRepository;
import com.example.ems.service.HodService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class HodServiceImpl implements HodService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;

    @Override
    @Transactional
    public EmployeeResponseDto createHod(HodDto hodDto) {

        log.info("Creating HOD with email: {}", hodDto.email());

        Department dept = departmentRepository.findById(hodDto.departmentId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Department not found with ID: " + hodDto.departmentId()));

        Hod hod = new Hod();
        hod.setName(hodDto.name());
        hod.setEmail(hodDto.email());
        hod.setSalary(hodDto.salary());
        hod.setAddress(toAddress(hodDto.address()));
        hod.setDepartment(dept);
        hod.setDepartments(java.util.Set.of(dept));

        Hod savedHod = (Hod) employeeRepository.save(hod);
        log.info("HOD created successfully with ID: {}", savedHod.getId());

        return toResponse(savedHod);
    }

    @Override
    @Transactional(readOnly = true)
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

    private Address toAddress(AddressDto dto) {
        return new Address(dto.city(), dto.state(), dto.pincode());
    }
}
