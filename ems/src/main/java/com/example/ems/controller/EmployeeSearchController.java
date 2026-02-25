package com.example.ems.controller;

import com.example.ems.dto.EmployeeResponseDto;
import com.example.ems.service.EmployeeSearchService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class EmployeeSearchController {

    private final EmployeeSearchService employeeSearchService;

    
    @GetMapping("/search")
    public ResponseEntity<List<EmployeeResponseDto>> searchEmployees(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String department,
            @RequestParam(required = false) Double minSalary,
            @RequestParam(required = false) Double maxSalary
    ) {

        List<EmployeeResponseDto> result =
                employeeSearchService.searchEmployees(
                        name,
                        email,
                        city,
                        department,
                        minSalary,
                        maxSalary
                );

        return ResponseEntity.ok(result);
    }
}
