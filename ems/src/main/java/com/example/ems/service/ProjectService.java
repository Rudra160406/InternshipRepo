package com.example.ems.service;

import com.example.ems.dto.ProjectDto;
import com.example.ems.dto.ProjectResponseDto;

import java.util.List;

public interface ProjectService {

    ProjectResponseDto createProject(ProjectDto projectDto);

    List<ProjectResponseDto> getProjectsByEmployee(Long employeeId);

    List<ProjectResponseDto> getAllProjects();

    ProjectResponseDto getProjectById(Long id);

    ProjectResponseDto updateProject(Long id, ProjectDto projectDto);

    void deleteProject(Long id);
}
