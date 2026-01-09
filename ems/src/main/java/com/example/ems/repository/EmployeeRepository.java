package com.example.ems.repository;

import com.example.ems.entity.Employee;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query("SELECT e FROM Employee e WHERE TYPE(e) = Employee")
    List<Employee> findAllNormalEmployees();

    @Query("SELECT e FROM Employee e WHERE TYPE(e) = Hod")
    List<Employee> findAllHods();
}
