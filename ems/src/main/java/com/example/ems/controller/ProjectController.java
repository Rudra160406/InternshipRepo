package com.example.ems.controller;

import com.example.ems.dto.ProjectDto;
import com.example.ems.dto.ProjectResponseDto;
import com.example.ems.service.ProjectService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@Slf4j
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping
    public ResponseEntity<ProjectResponseDto> createProject(
            @Valid @RequestBody ProjectDto projectDto
    ) {
        log.info("Creating project for employee ID: {}", projectDto.getEmployeeId());
        ProjectResponseDto response = projectService.createProject(projectDto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<ProjectResponseDto>> getProjectsByEmployee(
            @PathVariable Long employeeId
    ) {
        log.info("Fetching projects for employee ID: {}", employeeId);
        List<ProjectResponseDto> projects = projectService.getProjectsByEmployee(employeeId);
        return ResponseEntity.ok(projects);
    }

    @GetMapping
    public ResponseEntity<List<ProjectResponseDto>> getAllProjects() {
        log.info("Fetching all projects");
        List<ProjectResponseDto> projects = projectService.getAllProjects();
        return ResponseEntity.ok(projects);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectResponseDto> getProjectById(@PathVariable Long id) {
        return ResponseEntity.ok(projectService.getProjectById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjectResponseDto> updateProject(
            @PathVariable Long id,
            @Valid @RequestBody ProjectDto projectDto
    ) {
        return ResponseEntity.ok(projectService.updateProject(id, projectDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
        return ResponseEntity.noContent().build();
    }
}
