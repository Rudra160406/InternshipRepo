package com.example.ems.service.impl;

import com.example.ems.dto.ProjectDto;
import com.example.ems.dto.ProjectResponseDto;
import com.example.ems.dto.SimpleDepartmentDto;
import com.example.ems.entity.Employee;
import com.example.ems.entity.Project;
import com.example.ems.exception.ResourceNotFoundException;
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
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Employee not found with ID: " + dto.getEmployeeId()));

        Project project = new Project();
        applyProjectData(project, dto, employee);

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

    @Override
    public ProjectResponseDto getProjectById(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with ID: " + id));
        return mapToResponse(project);
    }

    @Override
    @Transactional
    public ProjectResponseDto updateProject(Long id, ProjectDto dto) {
        Project existing = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with ID: " + id));

        Employee employee = employeeRepository.findById(dto.getEmployeeId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Employee not found with ID: " + dto.getEmployeeId()));

        applyProjectData(existing, dto, employee);
        Project updated = projectRepository.save(existing);
        return mapToResponse(updated);
    }

    @Override
    @Transactional
    public void deleteProject(Long id) {
        Project existing = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with ID: " + id));
        projectRepository.delete(existing);
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
                emp.getAddress(),
                emp.getDepartments().stream()
                        .map(d -> new SimpleDepartmentDto(d.getId(), d.getDepartmentName()))
                        .toList()
        );
    }

    private void applyProjectData(Project project, ProjectDto dto, Employee employee) {
        project.setProjectName(dto.getProjectName());
        project.setStatus(dto.getStatus());
        project.setEmployee(employee);
    }
}
