package com.example.ems.controller;

import com.example.ems.dto.EmployeeResponseDto;
import com.example.ems.dto.HodDto;
import com.example.ems.service.HodService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hods")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class HodController {

    private final HodService hodService;

    @PostMapping
    public ResponseEntity<EmployeeResponseDto> createHod(
            @Valid @RequestBody HodDto hodDto
    ) {
        return ResponseEntity.ok(hodService.createHod(hodDto));
    }

    @GetMapping
    public ResponseEntity<List<EmployeeResponseDto>> getAllHods() {
        return ResponseEntity.ok(hodService.getAllHods());
    }
}
