package com.example.ems.service;

import com.example.ems.dto.EmployeeResponseDto;
import com.example.ems.entity.Hod;

import java.util.List;

public interface HodService {

    EmployeeResponseDto createHod(Hod hod);

    List<EmployeeResponseDto> getAllHods();
}
