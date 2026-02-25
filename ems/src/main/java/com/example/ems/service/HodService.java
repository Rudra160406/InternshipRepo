package com.example.ems.service;

import com.example.ems.dto.HodDto;
import com.example.ems.dto.EmployeeResponseDto;

import java.util.List;

public interface HodService {

    EmployeeResponseDto createHod(HodDto hodDto);

    List<EmployeeResponseDto> getAllHods();
}
