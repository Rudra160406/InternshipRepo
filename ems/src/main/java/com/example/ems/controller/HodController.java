package com.example.ems.controller;

import com.example.ems.dto.EmployeeResponseDto;
import com.example.ems.entity.Hod;
import com.example.ems.service.HodService;

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
            @RequestBody Hod hod
    ) {
        return ResponseEntity.ok(hodService.createHod(hod));
    }

    @GetMapping
    public ResponseEntity<List<EmployeeResponseDto>> getAllHods() {
        return ResponseEntity.ok(hodService.getAllHods());
    }
}
