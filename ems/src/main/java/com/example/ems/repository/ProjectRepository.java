package com.example.ems.repository;

import com.example.ems.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    List<Project> findByEmployeeId(Long employeeId);
}
