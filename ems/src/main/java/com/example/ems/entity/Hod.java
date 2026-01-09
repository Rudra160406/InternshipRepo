package com.example.ems.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@DiscriminatorValue("HOD")
@Getter
@Setter
@NoArgsConstructor
public class Hod extends Employee {

    @OneToOne
    @JoinColumn(name = "hod_department_id", unique = true)
    private Department department;
}
