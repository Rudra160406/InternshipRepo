package com.example.ems.service.impl;

import com.example.ems.dto.ProjectDto;
import com.example.ems.dto.ProjectResponseDto;
import com.example.ems.entity.Employee;
import com.example.ems.entity.Project;
import com.example.ems.repository.EmployeeRepository;
import com.example.ems.repository.ProjectRepository;
import com.example.ems.service.ProjectService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    @Transactional
    public ProjectResponseDto createProject(ProjectDto dto) {

        log.info("Creating project '{}' for employee ID {}", dto.getProjectName(), dto.getEmployeeId());

        Employee employee = employeeRepository.findById(dto.getEmployeeId())
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        Project project = new Project();
        project.setProjectName(dto.getProjectName());
        project.setStatus(dto.getStatus());
        project.setEmployee(employee);

        Project savedProject = projectRepository.save(project);

        return mapToResponse(savedProject);
    }

    @Override
    public List<ProjectResponseDto> getProjectsByEmployee(Long employeeId) {
        List<Project> projects = projectRepository.findByEmployeeId(employeeId);
        return projects.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Override
    public List<ProjectResponseDto> getAllProjects() {
        List<Project> projects = projectRepository.findAll();
        return projects.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    private ProjectResponseDto mapToResponse(Project project) {
        Employee emp = project.getEmployee();
        return new ProjectResponseDto(
                project.getId(),
                project.getProjectName(),
                project.getStatus(),
                emp.getId(),
                emp.getName(),
                emp.getEmail(),
                emp.getAddress()
        );
    }
}
