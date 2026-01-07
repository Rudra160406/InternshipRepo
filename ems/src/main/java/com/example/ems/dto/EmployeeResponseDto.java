package com.example.ems.dto;

import com.example.ems.entity.Address;
import java.util.List;

public record EmployeeResponseDto(
        Long id,
        String name,
        String email,
        Double salary,
        Address address,
        List<SimpleDepartmentDto> departments) {
}
